package domas.astrauskas.wordcounter.parser;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    byte[] getSortedResult(MultipartFile[] files);
}
