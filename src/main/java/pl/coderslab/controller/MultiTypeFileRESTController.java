package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.MultiTypeFileServiceImpl;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/file")
public class MultiTypeFileRESTController {

    private final MultiTypeFileServiceImpl multiTypeFileService;

    @Autowired
    public MultiTypeFileRESTController(MultiTypeFileServiceImpl multiTypeFileService) {
        this.multiTypeFileService = multiTypeFileService;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public MultiTypeFileDto uploadFile(@RequestParam("file") MultipartFile file) {
        return multiTypeFileService.saveFile(file);
    }

    @GetMapping(value = "/show/{id}")
    public ResponseEntity<?> displayById(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.upload(fileId);
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



//    @PostMapping("/uploadFile")
//    public MultiTypeFile uploadFile(@RequestParam("file") MultipartFile file,
//                                    @RequestParam("objectType") String objectType,
//                                    @RequestParam("entityId") Long entityId) {
//        String fileName = multiTypeFileService.storeFile(file, entityId, objectType);
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
//        return new MultiTypeFile(fileName,file.getContentType(),file.getSize(), fileDownloadUri);
//    }

//    @PostMapping("/assignFileToObject")
//    public MultiTypeFile assignFileToObject(@RequestParam("objectType") String objectType,
//                                            @RequestParam("entityId") Long entityId) {
//        return multiTypeFileService.saveFileWithObjectId(objectType, entityId);
//    }

        @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("entityId") Long entityId,
                                                 HttpServletRequest request) {

        String fileName = multiTypeFileService.getFileName(entityId);
        Resource resource = null;
        if(fileName !=null && !fileName.isEmpty()) {
            try {
                resource = multiTypeFileService.loadFileAsResource(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                //logger.info("Could not determine file type.");
            }
            // Fallback to the default content type if type could not be determined
            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/url/{id}")
    public String getURL(@PathVariable("id") Long id, HttpServletRequest request) {
//        return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return request.getRequestURL().toString();
    }

}


//    @GetMapping("/find/{id}")
//    public ResponseEntity<Image> getImg(@PathVariable("id") Long id) {
//        Image image = imageService.findById(id);
//        return ResponseEntity.ok().body(image);
////                .contentType(MediaType.parseMediaType(image.getFileType()))
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + image.getFileName() + "\"")
////                .body(new ByteArrayResource(image.getData()));
//    }