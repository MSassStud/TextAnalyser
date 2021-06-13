package hci.project.textanalyser.topic;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import hci.project.textanalyser.statistical.Token;
import hci.project.textanalyser.statistical.TokenFilter;
import hci.project.textanalyser.statistical.Tokenizer;

public class TopicExtractor {

    private final Map<String, Topic> topics;

    public TopicExtractor(Collection<Topic> topics) {
        this.topics = topics.stream()
            .collect(toMap(Topic::getTitle, Function.identity()));
    }

    public List<TopicInfo> extract(String text) {
        List<Token> tokens = new Tokenizer().tokenize(text, new TokenFilter());
//        return tokens.stream()
//            .flatMap(token -> {
//                Map<Topic, List<Token>> matching = new HashMap<>();
//                for (var entry : topics.entrySet()) {
//                    Topic topic = entry.getValue();
//                    if (topic.getKeywords().contains(token.getText())) {
//                        matching.putIfAbsent(topic, new ArrayList<>()).add(token);
//                    }
//                }
//        
//                return matching.entrySet().stream();
//            })
//            .distinct()
//            .map(TopicInfo::new)
//            .collect(toList());
        
        Map<Topic, List<Token>> matching = new HashMap<>();
        for (var token : tokens) {
            for (var entry : topics.entrySet()) {
                Topic topic = entry.getValue();
                if (topic.getKeywords().contains(token.getText())) {
                    matching.putIfAbsent(topic, new ArrayList<>()).add(token);
                }
            }
        }
        
        List<TopicInfo> topicInfos = new ArrayList<>();
        for (var entry : matching.entrySet()) {
            List<Integer> starts = entry.getValue().stream().map(Token::getStart).collect(toList());
            topicInfos.add(new TopicInfo(entry.getKey().getTitle(), entry.getKey().getEmojis(), starts));
        }
        
        return topicInfos;
    }
}
