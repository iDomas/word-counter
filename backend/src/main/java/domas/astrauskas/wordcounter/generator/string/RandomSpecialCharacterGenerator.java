package domas.astrauskas.wordcounter.generator.string;

public class RandomSpecialCharacterGenerator implements SpecialCharacterGenerator {
    @Override
    public char randomSpecialCharacter() {
        return (char) ((Math.random() * 31) + 33);
    }
}
