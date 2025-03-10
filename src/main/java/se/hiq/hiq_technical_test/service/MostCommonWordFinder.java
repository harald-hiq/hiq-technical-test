package se.hiq.hiq_technical_test.service;

import org.springframework.stereotype.Service;
import se.hiq.hiq_technical_test.entity.Text;

import java.util.Comparator;
import java.util.Map;

@Service
public class MostCommonWordFinder {
    public String findMostCommonWord(Text text) {
        return text.getWordCounts().entrySet()
                .stream()
                .min(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .orElseThrow();
    }
}
