package se.hiq.hiq_technical_test.service;

import org.springframework.stereotype.Service;

@Service
public class TextTransformer {
    public String process(String originalText, String toFind) {
        if (originalText == null || toFind == null) {
            throw new IllegalArgumentException("input cannot be null");
        }
        return originalText.replaceAll("\\b" + toFind + "\\b", "foo" + toFind + "bar");
    }
}
