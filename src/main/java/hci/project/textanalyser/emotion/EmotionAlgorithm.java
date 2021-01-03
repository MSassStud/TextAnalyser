package hci.project.textanalyser.emotion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EmotionAlgorithm {

    public List<String> getTopEmotions(String document) throws IOException {
        List<String> topEmotions = new ArrayList();

        topEmotions.add(Emotion.FEAR.name());

        sentiwsAnalyse(document);
        return topEmotions;
    }

    private boolean sentiwsAnalyse(String document) throws IOException {
        Resource resource = new ClassPathResource("static/emotion/sentiws/SentiWS_v2.0_Positive.txt");
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String positive = new String(bdata, StandardCharsets.UTF_8);

            String[] positives = positive.split("(.*\\|.*)\\s(\\d\\.\\d*)\\s(.*)");
            System.out.println(positive);
        }
        return false;
    }
}
