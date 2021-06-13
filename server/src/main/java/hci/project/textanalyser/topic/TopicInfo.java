package hci.project.textanalyser.topic;

import java.util.List;

public class TopicInfo {
    private final String title;
    private final String emojis;
    private final List<Integer> textMarks;
    
    public TopicInfo(String title, String emojis, List<Integer> textMarks) {
        this.title = title;
        this.emojis = emojis;
        this.textMarks = textMarks;
    }

    public String getTitle() {
        return title;
    }

    public String getEmojis() {
        return emojis;
    }

    public List<Integer> getTextMarks() {
        return textMarks;
    }
}
