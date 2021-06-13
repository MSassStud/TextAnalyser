package hci.project.textanalyser.statistical;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tokenizer {

    public List<Token> tokenize(String text, Predicate<String> filter) {
        String normalized = text.replace("\\s+", " ");
        List<Token> tokens = new ArrayList<>();
        int tokenStart = 0;
        int tokenEnd = 0;
        for (int i = 0; i < normalized.length(); i++) {
            String next = normalized.substring(i, i + 1);
            if (next.matches(" |\\.|,|!|\\?")) {
                if (tokenEnd - tokenStart > 0) {
                    String token = normalized.substring(tokenStart, tokenEnd);
                    tokens.add(new Token(token, tokenStart, tokenEnd));
                }
                String token = normalized.substring(i, i + 1);
                if (!token.isBlank()) {
                    tokens.add(new Token(token, i, i + 1));
                }
                tokenStart = i + 1;
                tokenEnd = i + 1;
            } else {
                tokenEnd++;
            }
        }
        if (tokenStart < normalized.length() - 1) {
            tokens.add(new Token(normalized.substring(tokenStart), tokenStart, normalized.length() - 1));
        }
        return tokens.stream()
            .filter(token -> filter.test(token.getText()))
            .collect(toList());
    }
}
