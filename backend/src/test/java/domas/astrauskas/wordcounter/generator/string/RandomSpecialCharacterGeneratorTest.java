package domas.astrauskas.wordcounter.generator.string;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class RandomSpecialCharacterGeneratorTest {

    private RandomSpecialCharacterGenerator generator;
    private List<Character> rangeOfSpecialCharacters = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        generator = new RandomSpecialCharacterGenerator();
        IntStream.range(33, 65).forEach((z) -> rangeOfSpecialCharacters.add((char) z));
    }

    @Test
    public void shouldGenerateRandomSpecialCharacter() {
        assertTrue(rangeOfSpecialCharacters.contains(generator.randomSpecialCharacter()));
    }

}
