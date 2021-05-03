package hci.project.textanalyser.statistical;

public class Keyword {
    private final String word;
    private final double rating;
    
    public Keyword(String word, double rating) {
        this.word = word;
        this.rating = rating;
    }
    
    @Override
    public String toString() {
        return getWord() + "(" + getRating() + ")";
    }

    public String getWord() {
        return word;
    }

    public double getRating() {
        return rating;
    }
}