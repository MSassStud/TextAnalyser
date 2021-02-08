package hci.project.textanalyser.statistical;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tokenizer {

    public List<String> tokenize(String text, Predicate<String> filter) {
        String normalized = text.replace("\\s+", " ");
        List<String> tokens = new ArrayList<>();
        int tokenStart = 0;
        int tokenEnd = 0;
        for (int i = 0; i < normalized.length(); i++) {
            String next = normalized.substring(i, i + 1);
            if (next.matches(" |\\.|,|!|\\?")) {
                if (tokenEnd - tokenStart > 0) {
                    String token = normalized.substring(tokenStart, tokenEnd);
                    tokens.add(token);
                }
                String token = normalized.substring(i, i + 1);
                if (!token.isBlank()) {
                    tokens.add(token);
                }
                tokenStart = i + 1;
                tokenEnd = i + 1;
            } else {
                tokenEnd++;
            }
        }
        if (tokenStart < normalized.length() - 1) {
            tokens.add(normalized.substring(tokenStart));
        }
        return tokens.stream()
            .filter(filter)
            .collect(toList());
    }
}
