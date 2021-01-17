package hci.project.textanalyser.sentiment;

import hci.project.textanalyser.sentiment.SentimentAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SentimentAnalyzerTest {

    private SentimentAnalyzer sentimentAnalyzer;

    @BeforeEach
    public void setUp() {
        sentimentAnalyzer = new SentimentAnalyzer();
    }

    @Test
    public void findSentiment() {

        String englishSentence = "This is a neutral sentence";
        String germanSentence = "Das ist ein neutraler Satz";
        Integer englishSentiment = sentimentAnalyzer.findSentiment(englishSentence);

        Integer germanSentiment = sentimentAnalyzer.findSentiment(germanSentence);

        assertEquals(2, englishSentiment);
        assertEquals(2, germanSentiment);
    }
}
