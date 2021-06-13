package hci.project.textanalyser.noun;

import java.util.List;

public class MockImageApi implements IMockImageApi {

    @Override
    public String findBy(List<Noun> criteria) {
        return "http://localhost:8080/dummy.gif";
    }
}
