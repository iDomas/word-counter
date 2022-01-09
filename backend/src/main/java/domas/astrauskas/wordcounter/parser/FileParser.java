package domas.astrauskas.wordcounter.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FileParser implements Callable<Map<LetterRange, Map<String, Integer>>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);

    private MultipartFile fileToParse;

    public FileParser(MultipartFile fileToParse) {
        this.fileToParse = fileToParse;
    }


    @Override
    public Map<LetterRange, Map<String, Integer>> call() {
        Map<LetterRange, Map<String, Integer>> result = new ConcurrentHashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(fileToParse.getInputStream(), StandardCharsets.UTF_8)
        )) {
            String[] words = reader.lines().collect(Collectors.joining()).split(" ");
            Arrays.stream(words)
                    .filter((word) -> word.matches("^\\w+"))
                    .forEach((word) -> countWords(word, result));

        } catch (IOException e) {

        }
        return result;
    }


    private Map<LetterRange, Map<String, Integer>> countWords(String word, Map<LetterRange, Map<String, Integer>> result) {
        short asciiIndex = (short) word.toUpperCase(Locale.ROOT).toCharArray()[0];
        if (asciiIndex > 64 && asciiIndex < 72) {
            distribute(word, result, LetterRange.AG);
        } else if (asciiIndex >= 72 && asciiIndex < 79) {
            distribute(word, result, LetterRange.HN);
        } else if (asciiIndex >= 79 && asciiIndex < 86) {
            distribute(word, result, LetterRange.OU);
        } else if (asciiIndex >= 86 && asciiIndex < 91) {
            distribute(word, result, LetterRange.VZ);
        }
        return result;
    }

    private Map<LetterRange, Map<String, Integer>> distribute(
            String word,
            Map<LetterRange, Map<String, Integer>> result,
            LetterRange letterRange) {
        Map<String, Integer> counted = result.get(letterRange);
        if (result.containsKey(letterRange)) {
            count(counted, word);
            result.put(letterRange, counted);
        } else {
            Map<String, Integer> countable = new ConcurrentHashMap<>();
            result.put(letterRange, count(countable, word));
        }

        return result;
    }

    private Map<String, Integer> count(Map<String, Integer> counted, String word) {
        if (counted.containsKey(word)) {
            counted.put(word, counted.get(word) + 1);
        } else {
            counted.put(word, 1);
        }
        return counted;
    }
}
