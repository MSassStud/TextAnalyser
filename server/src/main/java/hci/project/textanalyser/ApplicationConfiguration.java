package hci.project.textanalyser;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.Multimap;

import ch.qos.logback.access.servlet.TeeFilter;
import hci.project.textanalyser.noun.EmojiParser;
import hci.project.textanalyser.noun.INounToGif;
import hci.project.textanalyser.noun.MockImageApi;
import hci.project.textanalyser.noun.Noun;
import hci.project.textanalyser.noun.NounToGif;

@Configuration
public class ApplicationConfiguration {
    
    @Bean
    public Multimap<String, String> emojiIndex() throws XMLStreamException, IOException {
        ClassPathResource resource = new ClassPathResource("/cldr-common-38.1/annotation/en.xml");
        return new EmojiParser().createKeywordIndex(resource.getInputStream());
    }
    
    @ConditionalOnProperty(name = "api.image", havingValue = "mock")
    @Bean
    public INounToGif<List<Noun>> mockImageApi() {
        return new MockImageApi();
    }
    
    @ConditionalOnProperty(name = "api.image", havingValue = "giphy")
    @Bean
    public INounToGif<List<Noun>> giphyImageApi() {
        return new NounToGif();
    }
    
    @Bean
    public FilterRegistrationBean<TeeFilter> requestResponseLogger() {
        var filter = new TeeFilter();
        
        var registration = new FilterRegistrationBean<TeeFilter>();
        registration.setFilter(filter);
        registration.setName("Request/Response Logging Filter");
        registration.addUrlPatterns("/conversations");
        
        return registration;
    }
}
