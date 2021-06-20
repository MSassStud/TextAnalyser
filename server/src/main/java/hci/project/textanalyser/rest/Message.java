package hci.project.textanalyser.rest;

import java.time.LocalDateTime;
import java.util.Map;

public class Message {
    private final String id;
    private final LocalDateTime createTime;
    private final String from;
    private final String to;
    private final String content;
    private final AnalysedProperties analysedProperties;
    private final Map<String, Object> data;

    public Message(String id, LocalDateTime createTime, String from, String to, String content, AnalysedProperties analysedProperties, Map<String, Object> data) {
        this.id = id;
        this.createTime = createTime;
        this.from = from;
        this.to = to;
        this.content = content;
        this.analysedProperties = analysedProperties;
        this.data = data;
    }
    
    public String getId() {
        return id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
    
    public String getContent() {
        return content;
    }

    public AnalysedProperties getAnalysedProperties() { return analysedProperties; }

    public Map<String, Object> getData() {
        return data;
    }
}