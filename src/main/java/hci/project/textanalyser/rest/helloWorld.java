package hci.project.textanalyser.rest;

import hci.project.textanalyser.sentiment.Sentiment;
import hci.project.textanalyser.sentiment.SentimentAnalyzer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloWorld {

    @GetMapping
    public String hello() {
        return "Hello World!";
    }

    @PostMapping
    public AnalysedProperties analyseText(String text) {
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        Sentiment sentiment = sentimentAnalyzer.findSentiment(text);


        AnalysedProperties result = new AnalysedProperties();

        result.setSentiment(sentiment);

        return result;
    }
}
