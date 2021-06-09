package hci.project.textanalyser.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hci.project.textanalyser.noun.MappedEmoji;
import at.mukprojects.giphy4j.entity.search.SearchGiphy;
import hci.project.textanalyser.sentiment.Sentiment;
import hci.project.textanalyser.statistical.Keyword;
import hci.project.textanalyser.topic.TopicInfo;

public class AnalysedProperties {

    private Sentiment sentiment = null;
    private List<Keyword> keywords = new ArrayList<>();
    private List<TopicInfo> topics = new ArrayList<>();
    private List<String> emojis = new ArrayList<>();
    private SearchGiphy gif = null;

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

    public List<MappedEmoji> getEmojis() {
        return emojis;
    }

    public void setEmojis(List<MappedEmoji> emojis) {
        this.emojis = emojis;
    }

    public SearchGiphy getGif() { return gif; }

    public void setGifUrl(SearchGiphy gifUrl) { this.gif = gifUrl; }
}
