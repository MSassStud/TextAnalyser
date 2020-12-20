package hci.project.textanalyser.tfidf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.List;

import org.junit.jupiter.api.Test;

class TfIdfAlgorithmTest {

    @Test
    void extractsKeywords() {
        String document = "this is a test document";

        DocumentCollection collection = new DocumentCollection();
        collection.add(document);

        TfIdfAlgorithm extractor = new TfIdfAlgorithm(collection);
        List<String> keywords = extractor.extractKeywords(document);

        assertThat(keywords, containsInAnyOrder("this", "is", "a", "test", "document"));
    }

    @Test
    void assignsHigherScoreToInfrequentWordsInCollection() {
        var documents = List.of(
            "this is a test document",
            "this is a fake text",
            "this is a test string");

        DocumentCollection collection = new DocumentCollection();
        documents.forEach(collection::add);

        TfIdfAlgorithm extractor = new TfIdfAlgorithm(collection);

        List<String> keywords = extractor.extractKeywords(documents.get(0));
        assertThat(keywords.subList(0, 2), contains("document", "test"));

        keywords = extractor.extractKeywords(documents.get(1));
        assertThat(keywords.subList(0, 2), containsInAnyOrder("fake", "text"));

        keywords = extractor.extractKeywords(documents.get(2));
        assertThat(keywords.subList(0, 2), contains("string", "test"));
    }

    @Test
    void assignsHigherScoreToFrequentWordInDocument() {
        var documents = List.of(
            "a a a b",
            "c");

        DocumentCollection collection = new DocumentCollection();
        documents.forEach(collection::add);

        TfIdfAlgorithm extractor = new TfIdfAlgorithm(collection);

        List<String> keywords = extractor.extractKeywords(documents.get(0));
        assertThat(keywords.subList(0, 1), contains("a"));

        keywords = extractor.extractKeywords(documents.get(1));
        assertThat(keywords.subList(0, 1), containsInAnyOrder("c"));
    }
}
