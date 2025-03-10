package se.hiq.hiq_technical_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import se.hiq.hiq_technical_test.entity.Text;
import se.hiq.hiq_technical_test.service.MostCommonWordFinder;
import se.hiq.hiq_technical_test.service.TextNormalizer;
import se.hiq.hiq_technical_test.service.TextTokenizer;
import se.hiq.hiq_technical_test.service.TextTransformer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
public class TextProcessorController {
    private final TextNormalizer textNormalizer;
    private final TextTokenizer textTokenizer;
    private final MostCommonWordFinder mostCommonWordFinder;
    private final TextTransformer textTransformer;

    @Autowired
    public TextProcessorController(TextNormalizer textNormalizer,
                                   TextTokenizer textTokenizer,
                                   MostCommonWordFinder mostCommonWordFinder,
                                   TextTransformer textTransformer) {
        this.textNormalizer = textNormalizer;
        this.textTokenizer = textTokenizer;
        this.mostCommonWordFinder = mostCommonWordFinder;
        this.textTransformer = textTransformer;
    }


    @PostMapping("/")
    public String processText(@RequestParam("file") MultipartFile file) {
        final String originalString;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            originalString = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            //TODO
            return "An error occurred";
        }

        final String normalized = textNormalizer.normalize(originalString);
        final Text text = textTokenizer.tokenize(normalized);
        final String mostCommonWord = mostCommonWordFinder.findMostCommonWord(text);
        return textTransformer.process(originalString, mostCommonWord);
    }
}
