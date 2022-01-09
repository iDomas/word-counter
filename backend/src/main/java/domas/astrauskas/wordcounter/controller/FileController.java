package domas.astrauskas.wordcounter.controller;

import domas.astrauskas.wordcounter.parser.FileService;
import domas.astrauskas.wordcounter.parser.FileSortService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/file")
@RestController
public class FileController {

    private FileService fileService = new FileSortService();

    @CrossOrigin
    @PostMapping(value = "", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(fileService.getSortedResult(files));
    }

}
