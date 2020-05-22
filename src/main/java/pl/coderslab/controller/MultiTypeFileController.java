package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import org.springframework.core.io.Resource;
import pl.coderslab.service.MultiTypeFileService;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.MalformedURLException;
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
    @GetMapping("/showFile/{id}")
    public ResponseEntity<?> displayById(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.loadIntoBrowser(fileId);
    }

//    @ApiOperation(value = "Download a file by file id", response = URL.class)
//    @GetMapping("/downloadFile/{id}")
//    public ResponseEntity<?> downloadFile(@PathVariable("id") Long fileId,
//                                          HttpServletRequest request) {
//        Resource resource = multiTypeFileService.loadFileAsResource(fileId);
//        String contentType;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }

    //NIE WCZYTUJE PLIKÓW - jak naprawisz to zamień z
    //    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
//    @ApiOperation(value = "Upload files and receive list of urls for view", response = URL.class)
//    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST, consumes = {"multipart/form-data"})
//    public Set<URL> uploadFile(@RequestParam("files") MultipartFile[] files) {
//        return multiTypeFileService.uploadFiles(files);
//    }

    //Nie działa
//    @PostMapping("/createFromURL/{url}")
//    public void createFromURL(@PathVariable("url") String url) {
//        multiTypeFileService.saveFromURL(url);
//    }
//
//    //??
//    @GetMapping("/url/{id}")
//    public ResponseEntity<?> getURL(@PathVariable("id") Long fileId, HttpServletRequest request) throws MalformedURLException {
////        return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);;
//        return multiTypeFileService.uploadFromURL(fileId, new URL(request.getRequestURL().toString()));
//    }

}