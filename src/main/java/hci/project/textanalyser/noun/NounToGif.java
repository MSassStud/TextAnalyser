package hci.project.textanalyser.noun;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchGiphy;
import at.mukprojects.giphy4j.exception.GiphyException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NounToGif {
    private static final String API_KEY = "57X4b5DJFDgFCPvXdjEiM59BpwVlOH8Z";

    public String getGif(List<Noun> nouns) {

        Giphy giphy = new Giphy(API_KEY);

        try {
            SearchGiphy searchResult = giphy.translate(this.getMostCommonWordInNouns(nouns));
            return searchResult.getData().getEmbedUrl();
        } catch (GiphyException e) {
            return null;
        }
    }

    private String getMostCommonWordInNouns(final List<Noun> nouns) {
        List<String> words =
                nouns.stream()
                        .map(noun -> noun.words())
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

        return words.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();
    }
}
