package domas.astrauskas.wordcounter.generator.string;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomStringGeneratorTest {

    private RandomStringGenerator generator;

    @BeforeEach
    public void setUp() {
        generator = new RandomStringGenerator();
    }

    @Test
    public void shouldGenerateRandomString() {
        assertNotNull(generator.generateRandomString());
    }

}
