package se.hiq.hiq_technical_test.service;

import org.springframework.stereotype.Service;
import se.hiq.hiq_technical_test.entity.Text;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TextTokenizer {
    public Text tokenize(String textString) {
        if (textString == null) {
            throw new IllegalArgumentException("text cannot be null");
        }

        Map<String, Long> wordCountMap = Pattern.compile(" ")
                .splitAsStream(textString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new Text(textString, wordCountMap);
    }
}
