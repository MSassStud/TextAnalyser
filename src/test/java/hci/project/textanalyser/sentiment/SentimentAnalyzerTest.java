package hci.project.textanalyser.sentiment;

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
        Sentiment englishSentiment = sentimentAnalyzer.findSentiment(englishSentence);

        assertEquals(Sentiment.NEUTRAL, englishSentiment);
    }
}
