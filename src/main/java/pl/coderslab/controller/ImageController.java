package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.impl.ImageServiceImpl;
import pl.coderslab.model.Image;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageServiceImpl imageService;

    @Autowired
    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @RequestMapping( path = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("You must select a file!", HttpStatus.OK);
        }
        imageService.saveImage(file);
        return new ResponseEntity<>("-Successfully uploaded " + file.getOriginalFilename() + "-", new HttpHeaders(),
                HttpStatus.OK);
    }

//    @GetMapping("/find/{id}")
//    public ResponseEntity<Image> getImg(@PathVariable("id") Long id) {
//        Image image = imageService.findById(id);
//        return ResponseEntity.ok().body(image);
////                .contentType(MediaType.parseMediaType(image.getFileType()))
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + image.getFileName() + "\"")
////                .body(new ByteArrayResource(image.getData()));
//    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<byte[]> getImgById(@PathVariable("id") Long id) {
        Image foundedImg = imageService.findById(id);
        byte[] imageByte = foundedImg.getData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(foundedImg.getFileType()))
                .body(imageByte);
    }

}
