package hci.project.textanalyser.noun;

import java.util.List;

import at.mukprojects.giphy4j.entity.search.SearchGiphy;

public class MockImageApi implements INounToGif<List<Noun>> {

//    @Override
//    public String findBy(List<Noun> criteria) {
//        return "http://localhost:8080/dummy.gif";
//    }

    @Override
    public SearchGiphy findBy(List<Noun> nouns) {
        return new SearchGiphy();
    }
}
