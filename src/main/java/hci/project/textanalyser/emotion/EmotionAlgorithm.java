package hci.project.textanalyser.emotion;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmotionAlgorithm {

    private final List<SentiWsEntry> positiveWords;

    public EmotionAlgorithm() {
        Resource resource = new ClassPathResource("static/emotion/sentiws/SentiWS_v2.0_Positive.txt");
        List<SentiWsEntry> positiveWords = new ArrayList<>();

        try (InputStream inputStream = resource.getInputStream()) {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String positive = new String(bdata, StandardCharsets.UTF_8);

            System.out.println(Arrays.asList(positive.split("\\R")).get(0));
            positiveWords = Arrays.stream(positive.split("\\R"))
                    .filter(String::isEmpty)
                    .map(SentiWsEntry::new)
                    .collect(Collectors.toList());

            System.out.println(positiveWords.get(0).getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.positiveWords = positiveWords;
    }

    public List<String> getTopEmotions(String document) throws IOException {
        List<String> topEmotions = new ArrayList();

        topEmotions.add(Emotion.FEAR.name());
        sentiwsAnalyse(document);

        return topEmotions;
    }

    public Double sentiwsAnalyse(String document) throws IOException {
        return Arrays.stream(document.split("\\s"))
                .mapToDouble(this::getPositiveValue)
                .sum();
    }

    private Double getPositiveValue(String word) {
        System.out.println(positiveWords.toString());

        return positiveWords.stream()
                .filter(positive -> positive.contains(word))
                .findFirst()
                .map(SentiWsEntry::getValue)
                .orElse(Double.valueOf(0.0));
    }
}
