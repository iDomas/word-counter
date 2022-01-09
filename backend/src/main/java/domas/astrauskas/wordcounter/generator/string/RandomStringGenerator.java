package domas.astrauskas.wordcounter.generator.string;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomStringGenerator implements StringGenerator {

    private final SpecialCharacterGenerator specialCharacterGenerator;

    public RandomStringGenerator() {
        this.specialCharacterGenerator = new RandomSpecialCharacterGenerator();
    }

    @Override
    public String generateRandomString() {
        final int wordLength = (int) (Math.random() * 20);
        boolean appendSpecialCharacter = (int) (Math.random() * 3) > 1;
        return appendSpecialCharacter
                ? specialCharacterGenerator.randomSpecialCharacter() + RandomStringUtils.randomAlphabetic(wordLength)
                : RandomStringUtils.randomAlphabetic(wordLength);
    }
}
