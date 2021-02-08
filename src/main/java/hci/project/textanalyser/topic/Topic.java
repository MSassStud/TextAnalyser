package hci.project.textanalyser.topic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Topic {
    private final String title;
    private final Set<String> keywords = new HashSet<>();
    private final String emojis;
    
    public Topic(String title, Collection<String> keywords, String emojis) {
        this.title = title;
        this.keywords.addAll(keywords);
        this.emojis = emojis;
    }
    
    public String getTitle() {
        return title;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public String getEmojis() {
        return emojis;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!obj.getClass().equals(getClass()))
            return false;
        Topic other = (Topic) obj;
        return title.equals(other.title);
    }
}
