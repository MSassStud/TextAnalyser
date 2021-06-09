package hci.project.textanalyser.noun;

import at.mukprojects.giphy4j.entity.search.SearchGiphy;

import java.util.List;

public interface INounToGif {
    SearchGiphy findBy(List<Noun> nouns);
}
