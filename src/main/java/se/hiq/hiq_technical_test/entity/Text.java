package se.hiq.hiq_technical_test.entity;

import java.util.Map;

public class Text {
    private final String original;
    private final Map<String, Long> wordCounts;

    public Text(String text, Map<String, Long> wordCounts) {
        this.original = text;
        this.wordCounts = wordCounts;
    }

    public String getOriginal() {
        return original;
    }

    public Map<String, Long> getWordCounts() {
        return wordCounts;
    }
}
