package hci.project.textanalyser.sentiment;

public enum Sentiment {
    VERY_NEGATIVE(0),
    NEGATIVE(1),
    NEUTRAL(2),
    POSITIVE(3),
    VERY_POSITIVE(4);

    private int value;

    Sentiment(Integer value) {
        this.value = value;
    };

    public static Sentiment byValue(int input) {
        for (Sentiment sentiment : values()) {
            if (sentiment.value == input) {
                return sentiment;
            }
        }

        throw new IllegalArgumentException(String.valueOf(input));
    }
}
