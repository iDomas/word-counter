package domas.astrauskas.wordcounter.generator.file;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class PopulateFile implements Populate {

    @Override
    public void populate(String fileName, String words) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, new File(fileName).exists()))) {
            bufferedWriter.append(words);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + fileName, e);
        }
    }
}
