package hci.project.textanalyser.rest;

import hci.project.textanalyser.sentiment.Sentiment;

import java.util.List;

public class AnalysedProperties {

    private Sentiment sentiment = null;

    private List<String> emojis = null;

    private String gifUrl = null;

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Sentiment getSentiment() {
        return this.sentiment;
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
