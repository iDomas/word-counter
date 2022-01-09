package domas.astrauskas.wordcounter.generator.file;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class PopulateFileTest {

    private static final String FILE_NAME = "test.txt";

    private Populate populate;

    @BeforeEach
    public void setUp() {
        populate = new PopulateFile();
    }

    @AfterEach
    public void teardown() {
        final File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void shouldPopulateFile() throws IOException {
        final String fileContents = "TEST";
        populate.populate(FILE_NAME, fileContents);
        final File file = new File(FILE_NAME);
        assertTrue(file.exists());
        final String data = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        assertEquals(fileContents, data.trim());
    }

}
