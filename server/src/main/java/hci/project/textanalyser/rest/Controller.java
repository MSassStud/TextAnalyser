package hci.project.textanalyser.rest;

import static java.util.Map.entry;
import static java.util.stream.Collectors.joining;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import at.mukprojects.giphy4j.entity.search.SearchGiphy;
import hci.project.textanalyser.noun.INounToGif;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Multimap;

import hci.project.textanalyser.noun.EmojiMapper;
import hci.project.textanalyser.noun.MappedEmoji;
import hci.project.textanalyser.noun.Noun;
import hci.project.textanalyser.noun.NounExtractor;
import hci.project.textanalyser.sentiment.Sentiment;
import hci.project.textanalyser.sentiment.SentimentAnalyzer;
import hci.project.textanalyser.statistical.RatingKeywordExtractor;
import hci.project.textanalyser.topic.Topic;
import hci.project.textanalyser.topic.TopicExtractor;

@RestController
@CrossOrigin
public class Controller {

    private static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    
    private final Multimap<String, String> index;
    private final Map<String, Topic> topics = new HashMap<>();
    private final INounToGif<List<Noun>> nounToGif;
    private final Map<Conversation, List<Message>> conversations = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public class Conversation {
        private final String userA;
        private final String userB;

        public Conversation(String userA, String userB) {
            this.userA = userA;
            this.userB = userB;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + userA.hashCode() + userB.hashCode();
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Conversation other = (Conversation) obj;
            return Objects.equals(userA, other.userA) && Objects.equals(userB, other.userB)
                || Objects.equals(userA, other.userB) && Objects.equals(userB, other.userA);
        }
    }
    
    public class Message {
        private final String id;
        private final LocalDateTime createTime;
        private final String from;
        private final String to;
        private final String content;
        private final AnalysedProperties analysedProperties;
        private final Map<String, Object> data;

        public Message(String id, LocalDateTime createTime, String from, String to, String content, AnalysedProperties analysedProperties, Map<String, Object> data) {
            this.id = id;
            this.createTime = createTime;
            this.from = from;
            this.to = to;
            this.content = content;
            this.analysedProperties = analysedProperties;
            this.data = data;
        }
        
        public String getId() {
            return id;
        }

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
        
        public String getContent() {
            return content;
        }

        public AnalysedProperties getAnalysedProperties() { return analysedProperties; }

        public Map<String, Object> getData() {
            return data;
        }
    }

    public Controller(Multimap<String, String> index, INounToGif nounToGif) {
        this.index = index;
        this.nounToGif = nounToGif;
    }
    
    @PostMapping("/recording")
    public ResponseEntity<?> sendRecording(@RequestBody Map<String, String> body) throws FileNotFoundException, IOException {
        System.out.println("Received audio recording...");
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
        System.out.println(bdy);
        Map<String, Object> body = objectMapper.readValue(bdy, new TypeReference<Map<String, Object>>() {});
//        Map<String, String> data = objectMapper.readValue(content.get("data"), new TypeReference<Map<String, String>>() {});
        String from = (String) body.get("from");
        String to = (String) body.get("to");
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        List<Object> emojis = (List<Object>) data.get("emojis");
        System.out.println(emojis);
        
        var conversation = new Conversation(from, to);
        var message = new Message(UUID.randomUUID().toString(), LocalDateTime.now(), from, to, (String) body.get("message"), this.textToEmojis((String) body.get("message")), data);
        List<Message> messages = conversations.computeIfAbsent(conversation, key -> new LinkedList<Message>());
        messages.add(message);
        
//        List<MappedEmoji> emojis = message.getAnalysedProperties().getEmojis();
//        Map<String, Object> reply = Map.ofEntries(
//            entry("emojis", emojis));
        
//        return ResponseEntity.ok(reply);
        return ResponseEntity.noContent().build();
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
        
//        System.out.println(messages);
        
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/messages/{id}")
    public ResponseEntity<?> getMessage(@PathVariable String id) {
        
        return ResponseEntity.ok("");
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
