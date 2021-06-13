package hci.project.textanalyser;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import hci.project.textanalyser.noun.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.Multimap;

@Configuration
public class ApplicationConfiguration {
    
    @Bean
    public Multimap<String, String> emojiIndex() throws XMLStreamException, IOException {
        ClassPathResource resource = new ClassPathResource("/cldr-common-38.1/annotation/en.xml");
        return new EmojiParser().createKeywordIndex(resource.getInputStream());
    }
    
    @ConditionalOnProperty(name = "api.image", havingValue = "mock")
    @Bean
    public IMockImageApi mockImageApi() {
        return new MockImageApi();
    }
    
    @ConditionalOnProperty(name = "api.image", havingValue = "giphy")
    @Bean
    public INounToGif giphyImageApi() {
        return new NounToGif();
    }
}
