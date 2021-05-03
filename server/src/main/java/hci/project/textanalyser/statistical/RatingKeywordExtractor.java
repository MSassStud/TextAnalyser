package hci.project.textanalyser.statistical;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingKeywordExtractor {
    
    public List<Keyword> keywords(String text) {
        List<String> tokens = tokens(text);
        Map<String, Integer> occurences = occurences(tokens);
        Map<String, Double> ratings = ratings(occurences);
        List<Keyword> keywords = topN(ratings, 10);
        return keywords;
    }
    
    private List<String> tokens(String text) {
        return new Tokenizer().tokenize(text, new TokenFilter());
    }
    
    private Map<String, Integer> occurences(List<String> tokens) {
        return tokens.stream()
            .filter(new TokenFilter())
            .collect(toMap(identity(), term -> 1, (n, m) -> n + m));
    }
    
    private Map<String, Double> ratings(Map<String, Integer> occurences) {
        int words = occurences.values().stream()
            .mapToInt(Integer::valueOf)
            .sum();
        
        Map<String, Double> ratings = new HashMap<>();
        for (var entry : occurences.entrySet()) {
            ratings.put(entry.getKey(), entry.getValue() / (double) words);
        }
        
        return ratings;
    }
    
    private List<Keyword> topN(Map<String, Double> ratings, int n) {
        Comparator<Keyword> byRating = Comparator.comparing(keyword -> keyword.getRating());
        
        List<Keyword> keywords = ratings.entrySet().stream()
            .map(entry -> new Keyword(entry.getKey(), entry.getValue()))
            .sorted(byRating.reversed())
            .collect(toList());
        
        return keywords;
    }
}
