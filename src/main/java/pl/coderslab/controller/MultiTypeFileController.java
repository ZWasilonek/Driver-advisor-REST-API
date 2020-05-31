package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.MultiTypeFileService;

import java.net.URL;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/file")
public class MultiTypeFileController {

    private final MultiTypeFileService multiTypeFileService;

    @Autowired
    public MultiTypeFileController(MultiTypeFileService multiTypeFileService) {
        this.multiTypeFileService = multiTypeFileService;
    }

    @ApiOperation(value = "Upload a file and receive URL for view", response = URL.class)
    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
    public MultiTypeFileDto uploadFileFromURL(@RequestParam("file") MultipartFile file) {
        return multiTypeFileService.createFile(file);
    }

    @GetMapping("/find/{id}")
    public MultiTypeFileDto findFileById(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.findByFileId(fileId);
    }

    @PostMapping(path = "/update/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public MultiTypeFileDto updateFileById(@RequestPart("file") MultipartFile file,
                                           @PathVariable("id") Long fileId) {
        return multiTypeFileService.updateFile(file, fileId);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteFileById(@PathVariable("id") Long imageId) throws EntityNotFoundException {
        return multiTypeFileService.removeFileById(imageId);
    }

    @ApiOperation(value = "Display a file by file id from URL in browser", response = URL.class)
    @GetMapping("/showFile/{fileName}/{id}")
    public ResponseEntity<?> displayById(@PathVariable("fileName") String fileName,
                                         @PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.loadIntoBrowser(fileId);
    }

    @ApiOperation(value = "Download a file from database by file id", response = URL.class)
    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.downloadFileById(fileId);
    }

}