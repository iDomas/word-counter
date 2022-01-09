package domas.astrauskas.wordcounter.generator;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class WordFileGeneratorTest {

    private static final String FILE_NAME = "test.txt";
    private static final int NUM_OF_WORDS = 100;
    private static final String SPACE = " ";

    private WordFileGenerator generator;

    @BeforeEach
    public void setUp() {
        generator = new WordFileGenerator(NUM_OF_WORDS, FILE_NAME);
    }

    @AfterEach
    public void teardown() {
        final File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void shouldGenerateWordsFile() throws IOException {
        boolean isSuccessful = generator.call();
        assertTrue(isSuccessful);
        final String data = FileUtils.readFileToString(new File(FILE_NAME), StandardCharsets.UTF_8);
        assertEquals(NUM_OF_WORDS, data.trim().split(SPACE).length);
    }
}
