package hci.project.textanalyser.emotion;

import java.util.Arrays;
import java.util.List;

public class SentiWsEntry {

    String pattern = "(\\w*|\\w*)\\s(\\d\\.\\d*)\\s(\\.*)";

    private final double value;

    private final List<String> matches;

    public SentiWsEntry(String sentiWsEntryString) {
        System.out.println(sentiWsEntryString);

        String[] parsedEntry = sentiWsEntryString.split(pattern);
        System.out.println(Arrays.toString(parsedEntry));

        value = Double.valueOf(parsedEntry[1]);

        matches = Arrays.asList(parsedEntry[2].split(","));
        matches.add(parsedEntry[0].split("|")[0]);
    }

    public boolean matches(String word) {
        return matches.contains(word);
    }

    public double getValue() {
        return value;
    }
}
