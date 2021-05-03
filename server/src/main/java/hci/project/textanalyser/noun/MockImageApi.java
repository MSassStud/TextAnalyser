package hci.project.textanalyser.noun;

import java.util.List;

public class MockImageApi implements ImageApi<List<Noun>> {

    @Override
    public String findBy(List<Noun> criteria) {
        return "http://www.example.org";
    }
}
