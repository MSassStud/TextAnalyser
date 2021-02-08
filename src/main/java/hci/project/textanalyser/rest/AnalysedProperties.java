package hci.project.textanalyser.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hci.project.textanalyser.sentiment.Sentiment;
import hci.project.textanalyser.statistical.Keyword;
import hci.project.textanalyser.topic.TopicInfo;

public class AnalysedProperties {

    private Sentiment sentiment = null;
    private List<Keyword> keywords = new ArrayList<>();
    private List<TopicInfo> topics = new ArrayList<>();
    private List<String> emojis = new ArrayList<>();
    private String gifUrl = null;

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Sentiment getSentiment() {
        return this.sentiment;
    }
    
    public List<Keyword> getKeywords() {
        return keywords;
    }
    
    public List<TopicInfo> getTopics() {
        return topics;
    }

    public void setTopics(Collection<TopicInfo> topics) {
        this.topics = new ArrayList<>(topics);
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public List<String> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<String> emojis) {
        this.emojis = emojis;
    }

    public String getGifUrl() { return gifUrl; }

    public void setGifUrl(String gifUrl) { this.gifUrl = gifUrl; }   
}
