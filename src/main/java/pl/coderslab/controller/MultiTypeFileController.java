package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.MultiTypeFileServiceImpl;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/file")
public class MultiTypeFileController {

    private final MultiTypeFileServiceImpl multiTypeFileService;

    @Autowired
    public MultiTypeFileController(MultiTypeFileServiceImpl multiTypeFileService) {
        this.multiTypeFileService = multiTypeFileService;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public MultiTypeFileDto uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws MalformedURLException {
        MultiTypeFileDto fileDto = multiTypeFileService.saveFile(file);
        fileDto.setUploadDir(new URL(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileDto.getFileName())
                .toUriString()));
        return fileDto;
    }

    @GetMapping("/find/{id}")
    public MultiTypeFileDto findFileById(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.findById(fileId);
    }

    @PostMapping(path = "/update/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public MultiTypeFileDto updateFileById(@RequestPart("file") MultipartFile file,
                                           @PathVariable("id") Long fileId) {
        MultiTypeFileDto foundedImg = multiTypeFileService.findById(fileId);
        multiTypeFileService.updateFile(file, fileId);
        return foundedImg;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFileById(@PathVariable("id") Long imageId) throws EntityNotFoundException {
        multiTypeFileService.findById(imageId);
    }

    @ApiOperation(value = "Upload a file and receive url for view", response = URL.class)
    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
    public URL uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        MultiTypeFileDto fileDto = multiTypeFileService.saveFile(file);
        return multiTypeFileService.getURLForFile(fileDto.getId(), request);
    }

    @ApiOperation(value = "Display a file by file id from URL in browser", response = URL.class)
    @GetMapping("/showFile/{id}")
    public ResponseEntity<?> displayById(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.loadIntoBrowser(fileId);
    }

    @ApiOperation(value = "Download a file by file id", response = URL.class)
    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long fileId,
                                          HttpServletRequest request) {
        Resource resource = multiTypeFileService.loadFileAsResource(fileId);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //NIE WCZYTUJE PLIKÓW - jak naprawisz to zamień z
    //    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
//    @ApiOperation(value = "Upload files and receive list of urls for view", response = URL.class)
//    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST, consumes = {"multipart/form-data"})
//    public Set<URL> uploadFile(@RequestParam("files") MultipartFile[] files) {
//        return multiTypeFileService.uploadFiles(files);
//    }

    //Nie działa
    @PostMapping("/createFromURL/{url}")
    public void createFromURL(@PathVariable("url") String url) {
        multiTypeFileService.saveFromURL(url);
    }

    //??
    @GetMapping("/url/{id}")
    public ResponseEntity<?> getURL(@PathVariable("id") Long fileId, HttpServletRequest request) throws MalformedURLException {
//        return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);;
        return multiTypeFileService.uploadFromURL(fileId, new URL(request.getRequestURL().toString()));
    }

}