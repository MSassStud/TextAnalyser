package hci.project.textanalyser.topic;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import hci.project.textanalyser.statistical.TokenFilter;
import hci.project.textanalyser.statistical.Tokenizer;

public class TopicExtractor {

    private final Map<String, Topic> topics;

    public TopicExtractor(Collection<Topic> topics) {
        this.topics = topics.stream()
            .collect(toMap(Topic::getTitle, Function.identity()));
    }

    public List<TopicInfo> extract(String text) {
        List<String> tokens = new Tokenizer().tokenize(text, new TokenFilter());
        return tokens.stream()
            .flatMap(token -> {
                Set<Topic> matching = new HashSet<>();
                for (var entry : topics.entrySet()) {
                    Topic topic = entry.getValue();
                    if (topic.getKeywords().contains(token))
                        matching.add(topic);
                }
        
                return matching.stream();
            })
            .distinct()
            .map(TopicInfo::new)
            .collect(toList());
    }
}
