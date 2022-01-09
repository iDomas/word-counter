package domas.astrauskas.wordcounter.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface Zip {

    InputStream zipFiles(List<String> fileNames);

}
