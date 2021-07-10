package hci.project.textanalyser.gif;

import java.util.List;

import hci.project.textanalyser.noun.Noun;

public interface IMockImageApi {
    String findBy(List<Noun> criteria);
}
