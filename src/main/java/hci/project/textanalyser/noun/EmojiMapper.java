package hci.project.textanalyser.noun;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import com.google.common.collect.Multimap;

public class EmojiMapper {
    
    private final Multimap<String, String> index;

    public EmojiMapper(Multimap<String, String> index) {
        this.index = index;
    }
    
    public List<String> map(String text) throws XMLStreamException, IOException {
        NounExtractor extractor = NounExtractor.forCasedSentences();
        List<Noun> nouns = extractor.extract(text);
        
        return emojis(nouns);
    }
    
    public List<String> emojis(List<Noun> nouns) {
        List<String> mappedEmojis = new ArrayList<>();
        for (var noun : nouns) {
            if (noun.isPerson()) {
                mappedEmojis.add("🧑"); // person, neutral gender
            } else {
                String emojis = noun.words().stream()
                    .map(word -> {
                        Collection<String> lookup = index.get(word);
                        if (!lookup.isEmpty())
                            return lookup.iterator().next();
                        return "";
                    })
                    .collect(joining());
                if (!emojis.isEmpty()) {
                    mappedEmojis.add(emojis);
                }
            }
        }
        
        return mappedEmojis;
    }
}
