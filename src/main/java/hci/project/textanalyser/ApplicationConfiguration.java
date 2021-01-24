package hci.project.textanalyser;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.Multimap;

import hci.project.textanalyser.noun.EmojiParser;

@Configuration
public class ApplicationConfiguration {
    
    @Bean
    public Multimap<String, String> emojiIndex() throws XMLStreamException, IOException {
        ClassPathResource resource = new ClassPathResource("/cldr-common-38.1/annotation/en.xml");
        return new EmojiParser().createKeywordIndex(resource.getInputStream());
    }
}
