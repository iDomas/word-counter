package domas.astrauskas.wordcounter.util;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFiles implements Zip {

    private static final String ZIP_FILE_NAME = "result.zip";

    @Override
    public InputStream zipFiles(List<String> fileNames) {
        try (FileOutputStream fos = new FileOutputStream(ZIP_FILE_NAME);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            for (String fileName : fileNames) {
                File fileToZip = new File(fileName);
                if (!fileToZip.exists()) {
                    continue;
                }
                FileInputStream fis = new FileInputStream(fileToZip);

                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
        return getZipFile();
    }

    private InputStream getZipFile() {
        try {
            return new FileInputStream(ZIP_FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such file.", e);
        }
    }
}
