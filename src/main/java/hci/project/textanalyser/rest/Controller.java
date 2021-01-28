package hci.project.textanalyser.rest;

import static java.util.stream.Collectors.joining;

import java.util.List;

import hci.project.textanalyser.sentiment.Sentiment;
import hci.project.textanalyser.sentiment.SentimentAnalyzer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Multimap;

import hci.project.textanalyser.noun.EmojiMapper;
import hci.project.textanalyser.noun.Noun;
import hci.project.textanalyser.noun.NounExtractor;

@RestController
public class Controller {

    private final Multimap<String, String> index;

    public Controller(Multimap<String, String> index) {
        this.index = index;
    }

    @GetMapping
    public AnalysedProperties textToEmojis(String text) {

        AnalysedProperties result =  new AnalysedProperties();
        result.setSentiment(analyseText(text));
        result.setEmojis(getEmojis(text));

        return result;
    }

    private Sentiment analyseText(String text) {
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        return sentimentAnalyzer.findSentiment(text);
    }

    private List<String> getEmojis(String text) {
        NounExtractor nounExtractor = NounExtractor.forCasedSentences();
        List<Noun> nouns = nounExtractor.extract(text);
        EmojiMapper emojiMapper = new EmojiMapper(index);
        return emojiMapper.emojis(nouns);
    }
}
