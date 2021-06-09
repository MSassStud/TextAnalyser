package hci.project.textanalyser.noun;

import java.util.List;

public class MappedEmoji {
    private final String emoji;
    private final List<String> words;
    private final int textStart;
    private final int textEnd;
    
    public MappedEmoji(String emoji, int textStart, int textEnd, List<String> words) {
        this.emoji = emoji;
        this.textStart = textStart;
        this.textEnd = textEnd;
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getTextStart() {
        return textStart;
    }

    public int getTextEnd() {
        return textEnd;
    }
}
