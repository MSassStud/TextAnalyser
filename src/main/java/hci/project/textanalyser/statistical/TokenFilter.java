package hci.project.textanalyser.statistical;

import java.util.Set;
import java.util.function.Predicate;

public class TokenFilter implements Predicate<String> {

    // list copied from Apache Lucene
    // https://github.com/apache/lucene-solr/blob/master/lucene/analysis/common/src/java/org/apache/lucene/analysis/en/EnglishAnalyzer.java#L46
    private static final Set<String> STOPWORDS = Set.of("a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if", "in", "into", "is",
            "it", "no", "not", "of", "on", "or", "such", "that", "the", "their", "then", "there",
            "these", "they", "this", "to", "was", "will", "with");
    
    private static final Set<String> PUNCTUATION_TOKENS = Set.of(".", ",", "!", "?");
    
    @Override
    public boolean test(String token) {
        return !STOPWORDS.contains(token) && !PUNCTUATION_TOKENS.contains(token);
    }
}
