package hci.project.textanalyser.emojis;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hci.project.textanalyser.noun.Noun;

class EmojiMapper {
    
    private final EmojiIndex index;

    public EmojiMapper(EmojiIndex index) {
        this.index = index;
    }
    
//    public List<MappedEmoji> map(String text) throws XMLStreamException, IOException {
//        NounExtractor extractor = NounExtractor.forCasedSentences();
//        List<Noun> nouns = extractor.extract(text);
//        
//        return emojis(nouns);
//    }
    
    public List<MappedEmoji> emojis(List<Noun> nouns) {
        List<MappedEmoji> mappedEmojis = new ArrayList<>();
        for (var noun : nouns) {
            if (noun.isPerson()) {
                mappedEmojis.add(new MappedEmoji("🧑", noun.location().textStart(), noun.location().textEnd(), noun.words())); // person, neutral gender
            } else {
                String emojis = noun.words().stream()
                    .map(word -> {
                        Collection<String> lookup = index.getEmojis(word);
                        if (!lookup.isEmpty())
                            return lookup.iterator().next();
                        return "";
                    })
                    .collect(joining());
                if (!emojis.isEmpty()) {
                    mappedEmojis.add(new MappedEmoji(emojis, noun.location().textStart(), noun.location().textEnd(), noun.words()));
                }
            }
        }
        
        return mappedEmojis;
    }
}
