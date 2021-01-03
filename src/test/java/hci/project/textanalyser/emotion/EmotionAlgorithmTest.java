package hci.project.textanalyser.emotion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmotionAlgorithmTest {

    private static String FEAR = "";
    private static String ANGER = "";

    private EmotionAlgorithm emotionAlgorithm;

    @BeforeEach
    public void setup() {
        emotionAlgorithm = new EmotionAlgorithm();
    }

    @Test
    public void getTopEmotions_Fear() throws IOException {
        List<String> topEmotions = emotionAlgorithm.getTopEmotions(FEAR);
        assertEquals(1, topEmotions.size());
        assertEquals(Emotion.FEAR.name(), topEmotions.get(0));
    }

    @Test
    public void getTopEmotions_Anger() throws IOException {
        List<String> topEmotions = emotionAlgorithm.getTopEmotions(ANGER);
        assertEquals(1, topEmotions.size());
        assertEquals(Emotion.ANGER.name(), topEmotions.get(0));
    }

}
