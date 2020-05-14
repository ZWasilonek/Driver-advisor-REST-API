package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.impl.ImageServiceImpl;
import pl.coderslab.model.Image;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageServiceImpl imageService;

    @Autowired
    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("You must select a file!", HttpStatus.OK);
        }
        imageService.saveImage(file);
        return new ResponseEntity<>("-Successfully uploaded " + file.getOriginalFilename() + "-", new HttpHeaders(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<?> getImgById(@PathVariable("id") Long imageId) {
        Image foundedImg = imageService.findById(imageId);
        if (foundedImg != null) {
            byte[] imageByte = foundedImg.getData();
            return ok().contentType(valueOf(foundedImg.getFileType()))
                    .body(imageByte);
        }
        return new ResponseEntity<>("-Not file found with id: " + imageId + "-",HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/update/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImg(@RequestPart("file") MultipartFile file,
                                       @PathVariable("id") Long imageId) {
        Image foundedImg = imageService.findById(imageId);
        if (foundedImg == null) {
            return new ResponseEntity<>("There is no image with id: " + imageId, HttpStatus.NOT_FOUND);
        }
        if (file.isEmpty()) {
            return new ResponseEntity<>("You must select a file!", HttpStatus.NOT_FOUND);
        }
        imageService.updateImage(file, imageId);
        byte[] imageByte = foundedImg.getData();
        return ok().contentType(valueOf(foundedImg.getFileType()))
                .body(imageByte);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long imageId) {
        Image foundedImg = imageService.findById(imageId);
        if (foundedImg != null) {
            imageService.removeById(imageId);
            return new ResponseEntity<>("-Successfully removed file with id: " + imageId + "-", new HttpHeaders(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("-Not file found with id: " + imageId + "-",HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/findAll", produces = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<byte[]>> findAllImages() {
        List<byte[]> allImages = imageService.findAll().stream()
                .map(Image::getData)
                .collect(Collectors.toList());
        return new ResponseEntity<List<byte[]>>(allImages, HttpStatus.OK);
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