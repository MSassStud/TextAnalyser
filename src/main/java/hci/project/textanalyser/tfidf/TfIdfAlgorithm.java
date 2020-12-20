package hci.project.textanalyser.tfidf;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TfIdfAlgorithm {

    private final DocumentCollection collection;

    public TfIdfAlgorithm(DocumentCollection collection) {
        this.collection = collection;
    }

    public List<String> extractKeywords(String document) {
        List<String> terms = terms(document);
        System.out.println(terms);
        Map<String, Integer> occurences = occurences(terms);
        System.out.println(occurences);
        Map<String, Double> frequencies = frequencies(occurences);
        System.out.println(frequencies);
        Map<String, Double> scores = scores(frequencies);
        System.out.println(scores);
        List<String> collect = keywordsByScore(scores);
        return collect;
    }

    private List<String> keywordsByScore(Map<String, Double> scores) {
        Comparator<Entry<String, Double>> byScore = Comparator.comparing(Entry::getValue);
        List<String> collect = scores.entrySet().stream()
            .sorted(byScore.reversed())
            .map(Entry::getKey)
            .collect(toList());
        return collect;
    }

    private List<String> terms(String document) {
        return List.of(document.split(" "));
    }

    private Map<String, Integer> occurences(List<String> terms) {
        return terms.stream()
            .collect(toMap(identity(), term -> 1, (n, m) -> n + m));
    }

    private Map<String, Double> frequencies(Map<String, Integer> occurences) {
        int max = occurences.values().stream()
            .mapToInt(Integer::intValue).max()
            .orElseThrow(() -> new RuntimeException("analyzing empty document"));

        return occurences.entrySet().stream()
            .collect(toMap(Entry::getKey, entry -> (double) entry.getValue() / max));
    }

    private Map<String, Double> scores(Map<String, Double> frequencies) {
        return frequencies.entrySet().stream()
            .collect(toMap(Entry::getKey, entry -> Math.log((double) collection.documents() / collection.documentsContaining(entry.getKey()))));
    }
}
