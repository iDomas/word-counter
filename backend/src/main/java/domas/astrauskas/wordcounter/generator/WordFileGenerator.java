package domas.astrauskas.wordcounter.generator;

import domas.astrauskas.wordcounter.generator.file.Populate;
import domas.astrauskas.wordcounter.generator.file.PopulateFile;
import domas.astrauskas.wordcounter.generator.string.RandomStringGenerator;
import domas.astrauskas.wordcounter.generator.string.StringGenerator;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public final class WordFileGenerator implements Callable<Boolean> {

    private static final String SPACE = " ";
    private final Populate populateFile;
    private final StringGenerator stringGenerator;
    private int numOfWords;
    private String fileName;

    public WordFileGenerator(int numOfWords, String fileName) {
        this.stringGenerator = new RandomStringGenerator();
        this.populateFile = new PopulateFile();
        this.numOfWords = numOfWords;
        this.fileName = fileName;
    }

    @Override
    public Boolean call() {
        ArrayList<String> words = new ArrayList<>();
        boolean isSuccessful = Stream.iterate(numOfWords, (i) -> i != 0, (i) -> {
            words.add(stringGenerator.generateRandomString());
            return --i;
        }).count() == numOfWords;
        populateFile.populate(fileName, words.stream().reduce(" ", (acc, str) -> acc + str + SPACE));
        return isSuccessful;
    }
}
