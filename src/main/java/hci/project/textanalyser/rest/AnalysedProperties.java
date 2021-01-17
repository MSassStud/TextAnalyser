package hci.project.textanalyser.rest;

import hci.project.textanalyser.sentiment.Sentiment;

public class AnalysedProperties {

    private Sentiment sentiment = null;

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Sentiment getSentiment() {
        return this.sentiment;
    }
}
