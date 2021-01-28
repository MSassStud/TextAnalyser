package hci.project.textanalyser.rest;

import static java.util.stream.Collectors.joining;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Multimap;

import hci.project.textanalyser.noun.EmojiMapper;
import hci.project.textanalyser.noun.Noun;
import hci.project.textanalyser.noun.NounExtractor;

@RestController
public class Controller {

    private final Multimap<String, String> index;

    public Controller(Multimap<String, String> index) {
        this.index = index;
    }
    
    @GetMapping
    public String hello() {
        return "Hello World!";
    }
    
    @GetMapping(path = "/emojis")
    public String textToEmojis(String text) {
        NounExtractor nounExtractor = NounExtractor.forCasedSentences();
        List<Noun> nouns = nounExtractor.extract(text);
        EmojiMapper emojiMapper = new EmojiMapper(index);
        List<String> emojis = emojiMapper.emojis(nouns);
        
        return emojis.stream().collect(joining(" "));
    }
}
