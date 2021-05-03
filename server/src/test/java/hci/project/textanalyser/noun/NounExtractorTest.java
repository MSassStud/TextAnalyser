package hci.project.textanalyser.noun;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.Test;

public class NounExtractorTest {

    @Test
    void extractsNouns() {
        NounExtractor extractor = NounExtractor.forCasedSentences();
        List<Noun> extracted = extractor.extract("I own a large house. It has a black door.");
        
        assertAll(
            () -> assertThat(extracted.get(0).words(), contains("house")),
            () -> assertThat(extracted.get(1).words(), contains("house")),
            () -> assertThat(extracted.get(2).words(), contains("door"))
        );
    }
}
