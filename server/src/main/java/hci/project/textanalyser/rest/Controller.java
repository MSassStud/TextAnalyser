package hci.project.textanalyser.rest;

import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Multimap;

import at.mukprojects.giphy4j.entity.search.SearchGiphy;
import hci.project.textanalyser.noun.EmojiMapper;
import hci.project.textanalyser.noun.INounToGif;
import hci.project.textanalyser.noun.MappedEmoji;
import hci.project.textanalyser.noun.Noun;
import hci.project.textanalyser.noun.NounExtractor;
import hci.project.textanalyser.sentiment.Sentiment;
import hci.project.textanalyser.sentiment.SentimentAnalyzer;
import hci.project.textanalyser.statistical.RatingKeywordExtractor;
import hci.project.textanalyser.topic.Topic;
import hci.project.textanalyser.topic.TopicExtractor;
import hci.project.textanalyser.topic.TopicInfo;

@RestController
@CrossOrigin
public class Controller {

    private static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    
    private final Multimap<String, String> index;
    private final Map<String, Topic> topics = new HashMap<>();
    private final INounToGif<List<Noun>> nounToGif;
    private final Map<Conversation, List<Message>> conversations = new HashMap<>();
    private final Map<String, Message> messages = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Map<String, Topic>> userTopics = new HashMap<>();
    
    public Controller(Multimap<String, String> index, INounToGif<List<Noun>> nounToGif) {
        this.index = index;
        this.nounToGif = nounToGif;
    }
    
    @PostMapping("/users/{user}/topics")
    public ResponseEntity<?> createTopic(@PathVariable String user, @RequestBody Map<String, Object> body) {
        Map<String, Topic> topics = userTopics.computeIfAbsent(user, key -> new HashMap<>());
        
        String emoji = (String) body.get("emoji");
        String title = (String) body.get("title");
        String keywords = (String) body.get("keywords");
        
        List<String> keys = Stream.of(keywords.split(" "))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(toList());
        
        String id = UUID.randomUUID().toString();
        Topic topic = new Topic(id, title, keys, emoji);
        topics.put(id, topic);
        
        URI location = UriComponentsBuilder.fromPath("/users/{user}/topics/{id}").build(user, id);
        return ResponseEntity.created(location).build();
    }
    
    @PutMapping("/users/{user}/topics/{id}")
    public ResponseEntity<?> updateTopic(@PathVariable String user, @PathVariable String id, @RequestBody Map<String, Object> body) {
        String emoji = (String) body.get("emoji");
        String title = (String) body.get("title");
        String keywords = (String) body.get("keywords");
        
        List<String> keys = Stream.of(keywords.split(" "))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(toList());
        
        Topic topic = new Topic(id, title, keys, emoji);
        userTopics.get(user).put(id, topic);

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/users/{user}/topics/{id}")
    public ResponseEntity<?> getTopic(@PathVariable String user, @PathVariable String id) {
        Topic topic = userTopics.get(user).get(id);
        
        Map<String, String> mappedTopic = mapTopic(topic);
        
        return ResponseEntity.ok(mappedTopic);
    }
    
    @GetMapping("/users/{user}/topics")
    public ResponseEntity<?> getTopics(@PathVariable String user) {
        Map<String, Topic> topics = userTopics.getOrDefault(user, new HashMap<>());
        
        List<Map<String, String>> mappedTopics = topics.values()
            .stream()
            .map(this::mapTopic)
            .collect(toList());
        
        return ResponseEntity.ok(mappedTopics);
    }
    
    private Map<String, String> mapTopic(Topic topic) {
        Map<String, String> map = new HashMap<>();
        map.put("id", topic.getId());
        map.put("emoji", topic.getEmojis());
        map.put("title", topic.getTitle());
        map.put("keywords", topic.getKeywords().stream().sorted().collect(joining(" ")));
        return map;
    }
    
    @PostMapping("/recording")
    public ResponseEntity<?> sendRecording(@RequestBody Map<String, String> body) throws FileNotFoundException, IOException {
//        String recording = body.get("recording");
//        
//        if (!recording.isEmpty()) {
//            byte[] bytes = Base64.getDecoder().decode(recording);
//            try (var out = new FileOutputStream(new File(""))) {
//                out.write(bytes);
//            }
//        }
        
        return ResponseEntity.ok(Map.of("message", "This is a placeholder for audio messages."));
    }
    
    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody String bdy) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> body = objectMapper.readValue(bdy, new TypeReference<Map<String, Object>>() {});
//        Map<String, String> data = objectMapper.readValue(content.get("data"), new TypeReference<Map<String, String>>() {});
        String from = (String) body.get("from");
        String to = (String) body.get("to");
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        List<Object> emojis = (List<Object>) data.get("emojis");
        
        var conversation = new Conversation(from, to);
        var message = new Message(UUID.randomUUID().toString(), LocalDateTime.now(), from, to, (String) body.get("message"), this.textToEmojis((String) body.get("message")), data);
        List<Message> conversationMessages = conversations.computeIfAbsent(conversation, key -> new LinkedList<Message>());
        conversationMessages.add(message);
        messages.put(message.getId(), message);
        
        URI location = UriComponentsBuilder.fromPath("/messages/" + message.getId()).build().toUri();
        return ResponseEntity.created(location).build();
    }
    
    @PostMapping("/preview")
    public ResponseEntity<?> preview(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        AnalysedProperties properties = textToEmojis(message);
        
        List<MappedEmoji> emojis = properties.getEmojis();
        Map<String, Object> reply = Map.ofEntries(
            entry("message", message),
            entry("emojis", emojis));
        
        return ResponseEntity.ok(reply);
    }
    
    @GetMapping("/conversations")
    public ResponseEntity<?> getConversation(@RequestParam String a, @RequestParam String b) {
        var conversation = new Conversation(a, b);
        List<Message> messages = conversations.getOrDefault(conversation, new LinkedList<>());
        
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/conversations2")
    public ResponseEntity<?> getConversation2(@RequestParam String a, @RequestParam String b) {
        var conversation = new Conversation(a, b);
        List<Message> messages = conversations.getOrDefault(conversation, new LinkedList<>());
        
        Collection<Topic> topics2 = userTopics.getOrDefault(a, new HashMap<>()).values();
        TopicExtractor topicExtractor = new TopicExtractor(topics2);
        List<Map<String, Object>> mappedMessages = new LinkedList<>();
        for (var message : messages) {
            List<TopicInfo> ts = topicExtractor.extract(message.getContent());
            
            Map<String, Object> reply = new HashMap<>();
            reply.put("id", message.getId());
            reply.put("from", message.getFrom());
            reply.put("to", message.getTo());
            reply.put("time", message.getCreateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH)));
            reply.put("mentions", ts);
            SearchGiphy gif = message.getAnalysedProperties().getGif();
            if (gif != null) {
                reply.put("gifUrl", gif.getData().getImages().getOriginal().getUrl());
            }
            
            mappedMessages.add(reply);
        }
        
        return ResponseEntity.ok(mappedMessages);
    }
    
    @GetMapping("/messages/{id}")
    public ResponseEntity<?> getMessage(@PathVariable String id, @RequestParam String user) {
        Message message = messages.get(id);
        List<TopicInfo> ts = new TopicExtractor(userTopics.getOrDefault(user, new HashMap<>()).values()).extract(message.getContent());
        
        Map<String, Object> reply = new HashMap<>();
        reply.put("id", message.getId());
        reply.put("from", message.getFrom());
        reply.put("to", message.getTo());
        reply.put("text", message.getContent());
        reply.put("emojis", message.getData().get("emojis"));
        reply.put("mentions", ts);
        
        return ResponseEntity.ok(reply);
    }
    
    @PutMapping(path = "/topics/{title}")
    public ResponseEntity<?> putTopic(@PathVariable String title, @RequestBody Topic topic) {
        topics.put(topic.getTitle(), topic);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping(path = "/topics/{title}")
    public ResponseEntity<?> deleteTopic(@PathVariable String title) {
        topics.remove(title);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public AnalysedProperties textToEmojis(@RequestBody String text) {
        List<Noun> nouns = extractNouns(text);
        
        AnalysedProperties result =  new AnalysedProperties();
        result.setSentiment(analyseText(text));
        result.setKeywords(new RatingKeywordExtractor().keywords(text));
        result.setTopics(new TopicExtractor(topics.values()).extract(text));
        result.setEmojis(getEmojis(nouns));
        result.setGifUrl(getGif(nouns));

        return result;
    }
    
    private List<Noun> extractNouns(String text) {
        NounExtractor nounExtractor = NounExtractor.forCaselessSentences();
        List<Noun> nouns = nounExtractor.extract(text);
        
        logNouns(nouns);
        return nouns;
    }

    private void logNouns(List<Noun> nouns) {
        LOGGER.info(nouns.stream()
            .map(n -> n.words().stream().collect(joining(" ")))
            .collect(joining(", ")));
    }

    private Sentiment analyseText(String text) {
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        return sentimentAnalyzer.findSentiment(text);
    }

    private List<MappedEmoji> getEmojis(List<Noun> nouns) {
        EmojiMapper emojiMapper = new EmojiMapper(index);
        return emojiMapper.emojis(nouns);
    }

    private SearchGiphy getGif(List<Noun> nouns) {
        return nounToGif.findBy(nouns);
    }
}
