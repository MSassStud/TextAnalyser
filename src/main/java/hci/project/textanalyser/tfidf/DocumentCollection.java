package hci.project.textanalyser.tfidf;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DocumentCollection {

    private int documents = 0;
    private Map<String, Integer> termOccurences = new HashMap<>();

    public void add(String document) {
        documents++;

        Stream.of(terms(document))
            .distinct()
            .forEach(term -> termOccurences.merge(term, 1, (n, m) -> n + m));
    }

    private String[] terms(String document) {
        return document.split(" ");
    }

    public int documentsContaining(String term) {
        return termOccurences.getOrDefault(term, 0);
    }

    public int documents() {
        return documents;
    }
}
