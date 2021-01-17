package hci.project.textanalyser.emotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SentiWsEntry {

    private final double value;

    private final List<String> matches;

    public SentiWsEntry(String sentiWsEntryString) {
        String[] parsedEntry = sentiWsEntryString.split("\t");

        value = Double.valueOf(parsedEntry[1]);

        String firstMatch = parsedEntry[0].split("\\|")[0];
        ArrayList<String> matches = new ArrayList<>();
        matches.addAll(Arrays.asList(parsedEntry[2].split(",")));
        matches.add(firstMatch);

        this.matches = matches;
    }

    public boolean contains(String word) {
        return matches.contains(word);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
