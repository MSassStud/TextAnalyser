package hci.project.textanalyser.noun;

import java.util.List;

import hci.project.textanalyser.Analyzer;

public class NounAnalyzer implements Analyzer<String, List<Noun>> {

    private final NounExtractor nounExtractor;

    public NounAnalyzer(NounExtractor nounExtractor) {
        this.nounExtractor = nounExtractor;
    }
    
    @Override
    public List<Noun> analyze(String text) {
        return nounExtractor.extract(text);
    }
}
