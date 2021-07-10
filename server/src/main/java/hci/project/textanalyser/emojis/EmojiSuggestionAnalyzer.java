package hci.project.textanalyser.emojis;

import java.util.List;

import hci.project.textanalyser.Analyzer;
import hci.project.textanalyser.noun.Noun;

public class EmojiSuggestionAnalyzer implements Analyzer<List<Noun>, List<MappedEmoji>> {

    private final EmojiMapper emojiMapper;

    public EmojiSuggestionAnalyzer(EmojiMapper emojiMapper) {
        this.emojiMapper = emojiMapper;
    }
    
    @Override
    public List<MappedEmoji> analyze(List<Noun> nouns) {
        return emojiMapper.emojis(nouns);
    }
}
