package hci.project.textanalyser;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ch.qos.logback.access.servlet.TeeFilter;
import hci.project.textanalyser.emojis.EmojiConfiguration;
import hci.project.textanalyser.gif.INounToGif;
import hci.project.textanalyser.gif.MockImageApi;
import hci.project.textanalyser.gif.NounToGif;
import hci.project.textanalyser.noun.Noun;

@Configuration
@Import({
    EmojiConfiguration.class
})
public class ApplicationConfiguration {
    
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
