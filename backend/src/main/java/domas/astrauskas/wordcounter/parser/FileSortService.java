package domas.astrauskas.wordcounter.parser;

import domas.astrauskas.wordcounter.generator.file.Populate;
import domas.astrauskas.wordcounter.generator.file.PopulateFile;
import domas.astrauskas.wordcounter.util.Zip;
import domas.astrauskas.wordcounter.util.ZipFiles;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileSortService implements FileService {

    private static final String EXTENSION = ".txt";

    private Populate populateFile;
    private Zip zipFiles;

    public FileSortService() {
        this.populateFile = new PopulateFile();
        zipFiles = new ZipFiles();
    }

    @Override
    public byte[] getSortedResult(MultipartFile[] files) {
        Arrays.stream(files)
                .parallel()
                .map((file) -> new FileParser(file).call())
                .reduce(new ConcurrentHashMap<>(), this::expandMap)
                .forEach((letterRange, usageInfo) ->
                        populateFile.populate(letterRange.name() + EXTENSION, getUsageInfoAsString(usageInfo)));

        InputStream zipFile = zipFiles.zipFiles(getFileNames());
        try {
            byte[] zipped = IOUtils.toByteArray(zipFile);

            getFileNames().forEach(file -> new File(file).delete());

            return zipped;
        } catch (IOException e) {
            throw new RuntimeException("Problem reading zipFile to bytes", e);
        }
    }

    private Map<LetterRange, Map<String, Integer>> expandMap(
            Map<LetterRange, Map<String, Integer>> main,
            Map<LetterRange, Map<String, Integer>> other) {
        main.putAll(other);
        return main;
    }

    private String getUsageInfoAsString(Map<String, Integer> usageInfo) {
        return usageInfo.entrySet()
                .stream()
                .map((entry) -> entry.getKey() + ", " + entry.getValue() + ",\n")
                .sorted()
                .reduce("word, numOfUsage, \n", (acc, curr) -> acc + curr);
    }

    private List<String> getFileNames() {
        List<String> fileNames = new ArrayList<>();
        fileNames.add(LetterRange.AG.name() + EXTENSION);
        fileNames.add(LetterRange.HN.name() + EXTENSION);
        fileNames.add(LetterRange.OU.name() + EXTENSION);
        fileNames.add(LetterRange.VZ.name() + EXTENSION);
        return fileNames;
    }
}
