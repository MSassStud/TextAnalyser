package hci.project.textanalyser.emotion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmotionAlgorithmTest {

    private static String TEST_INPUT = "Ich habe heute Chinesisch gegessen.";

    private EmotionAlgorithm emotionAlgorithm;

    @BeforeEach
    public void setup() {
        emotionAlgorithm = new EmotionAlgorithm();
    }

    @Test
    public void sentiwsAnalyse() throws IOException {
        Double result = emotionAlgorithm.sentiwsAnalyse(TEST_INPUT);

        assertEquals(Double.valueOf("0.01"), result);
    }

}
