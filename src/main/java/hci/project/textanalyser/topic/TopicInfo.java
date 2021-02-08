package hci.project.textanalyser.topic;

public class TopicInfo {
    private final String title;
    private final String emojis;
    
    public TopicInfo(Topic topic) {
        this(topic.getTitle(), topic.getEmojis());
    }
    
    public TopicInfo(String title, String emojis) {
        this.title = title;
        this.emojis = emojis;
    }

    public String getTitle() {
        return title;
    }

    public String getEmojis() {
        return emojis;
    }
}
