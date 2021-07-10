package hci.project.textanalyser.gif;

import at.mukprojects.giphy4j.entity.search.SearchGiphy;

public interface INounToGif<C> {
    SearchGiphy findBy(C nouns);
}
