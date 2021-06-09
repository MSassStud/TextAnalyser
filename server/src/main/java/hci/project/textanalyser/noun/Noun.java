package hci.project.textanalyser.noun;

import static java.util.stream.Collectors.joining;

import java.util.List;

public class Noun {
    private final Location location;
    private final List<String> words;
    private final boolean person;
    
    public Noun(Location location, List<String> words, boolean person) {
        this.location = location;
        this.words = words;
        this.person = person;
    }
    
    public boolean contains(Noun noun) {
        return location.contains(noun.location);
    }
    
    public Location location() {
        return location;
    }

    public List<String> words() {
        return words;
    }

    public boolean isPerson() {
        return person;
    }

    @Override
    public String toString() {
        return String.format("%s => %s %s", location, words.stream().collect(joining(" ")), person == true ? "[person]" : "");
    }
    
    public static class Location {
        private final int textStart;
        private final int sentence;
        private final int startToken;
        private final int endToken;
        private final int textEnd;

        public Location(int textStart, int textEnd, int sentence, int startToken, int endToken) {
            this.textStart = textStart;
            this.textEnd = textEnd;
            this.sentence = sentence;
            this.startToken = startToken;
            this.endToken = endToken;
        }
        
        public boolean contains(Location location) {
            return sentence == location.sentence && startToken <= location.startToken && endToken >= location.endToken;
        }
        
        public int textStart() {
            return textStart;
        }
        
        public int textEnd() {
            return textEnd;
        }
        
        public int sentence() {
            return sentence;
        }

        public int startToken() {
            return startToken;
        }

        public int endToken() {
            return endToken;
        }
        
        @Override
        public String toString() {
            return String.format("(%d,%d,%d)", sentence, startToken, endToken);
        }
    }
}