package hci.project.textanalyser.noun;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NounConfiguration {

    @Bean
    public NounAnalyzer nounAnalyzer() {
        return new NounAnalyzer(NounExtractor.forCasedSentences());
    }
}
