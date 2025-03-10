package se.hiq.hiq_technical_test.service;

import org.springframework.stereotype.Service;

@Service
public class TextNormalizer {
    public String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("\\W", " ")
                .replaceAll("\\n", " ")
                .replaceAll("\\s+", " ")
                .strip();
    }
}
