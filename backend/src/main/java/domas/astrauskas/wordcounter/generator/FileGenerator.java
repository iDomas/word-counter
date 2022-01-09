package domas.astrauskas.wordcounter.generator;

import domas.astrauskas.wordcounter.generator.string.RandomStringGenerator;
import domas.astrauskas.wordcounter.generator.string.StringGenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileGenerator implements Generator {

    private static final String EXTENSION = ".txt";
    private final ExecutorService executorsService;
    private final StringGenerator stringGenerator;

    public FileGenerator() {
        executorsService = Executors.newFixedThreadPool(10);
        stringGenerator = new RandomStringGenerator();
    }

    @Override
    public void generate(int numOfFiles) {
        for (int i = numOfFiles; i > 0; i--) {
            final int numOfWords = (int) (Math.random() * 1000);
            executorsService.submit(
                    new WordFileGenerator(numOfWords, stringGenerator.generateRandomString() + EXTENSION));
        }
    }
}
