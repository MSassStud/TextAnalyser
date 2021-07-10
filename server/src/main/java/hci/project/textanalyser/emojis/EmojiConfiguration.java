package hci.project.textanalyser.emojis;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class EmojiConfiguration {

    @Bean
    public EmojiIndex emojiIndex() throws IOException, XMLStreamException {
        ClassPathResource resource = new ClassPathResource("/cldr-common-38.1/annotation/en.xml");
        try (var stream = resource.getInputStream()) {
            return EmojiIndex.buildIndex(resource.getInputStream());
        }
    }
    
    @Bean
    public EmojiMapper emojiMapper(EmojiIndex index) {
        return new EmojiMapper(index);
    }
    
    @Bean
    public EmojiSuggestionAnalyzer emojiSuggestionAnalyzer(EmojiMapper emojiMapper) {
        return new EmojiSuggestionAnalyzer(emojiMapper);
    }
}
