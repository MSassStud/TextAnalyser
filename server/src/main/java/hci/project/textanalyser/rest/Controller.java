package hci.project.textanalyser.rest;

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

import com.google.common.collect.Multimap;

import hci.project.textanalyser.noun.EmojiMapper;
import hci.project.textanalyser.noun.ImageApi;
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
    private final ImageApi<List<Noun>> imageApi;
    private final Map<String, Topic> topics = new HashMap<>();
    private final Map<Conversation, List<Message>> conversations = new HashMap<>();
    
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

        public Message(String id, LocalDateTime createTime, String from, String to, String content, AnalysedProperties analysedProperties) {
            this.id = id;
            this.createTime = createTime;
            this.from = from;
            this.to = to;
            this.content = content;
            this.analysedProperties = analysedProperties;
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
    }

    public Controller(Multimap<String, String> index, ImageApi<List<Noun>> imageApi) {
        this.index = index;
        this.imageApi = imageApi;
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
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> body) {
        String from = body.get("from");
        String to = body.get("to");
        var conversation = new Conversation(from, to);
        var message = new Message(UUID.randomUUID().toString(), LocalDateTime.now(), from, to, body.get("message"), this.textToEmojis(body.get("message")));
        List<Message> messages = conversations.computeIfAbsent(conversation, key -> new LinkedList<Message>());
        messages.add(message);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/conversations")
    public ResponseEntity<?> getConversation(@RequestParam String a, @RequestParam String b) {
        var conversation = new Conversation(a, b);
        List<Message> messages = conversations.getOrDefault(conversation, new LinkedList<>());
        
        System.out.println(messages);
        
        return ResponseEntity.ok(messages);
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

    private List<String> getEmojis(List<Noun> nouns) {
        EmojiMapper emojiMapper = new EmojiMapper(index);
        return emojiMapper.emojis(nouns);
    }

    private String getGif(List<Noun> nouns) {
        return imageApi.findBy(nouns);
    }
}
