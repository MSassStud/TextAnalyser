package hci.project.textanalyser.rest;

import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class Controller {

    private static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    
    private final Multimap<String, String> index;
    private final ImageApi<List<Noun>> imageApi;
    private final Map<String, Topic> topics = new HashMap<>();

    public Controller(Multimap<String, String> index, ImageApi<List<Noun>> imageApi) {
        this.index = index;
        this.imageApi = imageApi;
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
