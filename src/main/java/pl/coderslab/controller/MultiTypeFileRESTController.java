//package pl.coderslab.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.expression.ExpressionException;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import pl.coderslab.impl.MultiTypeFileServiceImpl;
//import pl.coderslab.model.MultiTypeFile;
//
//import static org.springframework.http.MediaType.*;
//import static org.springframework.http.ResponseEntity.ok;
//
//@RestController
//@RequestMapping("/image")
//public class MultiTypeFileRESTController {
//
//    private final MultiTypeFileServiceImpl multiTypeFileService;
//
//    @Autowired
//    public MultiTypeFileRESTController(MultiTypeFileServiceImpl multiTypeFileService) {
//        this.multiTypeFileService = multiTypeFileService;
//    }
//
//    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA_VALUE)
//    public MultiTypeFile uploadFile(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("You must select a file!", HttpStatus.OK);
//        }
//        multiTypeFileService.saveImage(file);
//        return new ResponseEntity<>("-Successfully uploaded " + file.getOriginalFilename() + "-", new HttpHeaders(),
//                HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/find/{id}")
//    public MultiTypeFile getImgById(@PathVariable("id") Long fileId) {
//        return multiTypeFileService.findById(fileId);
//    }
//
//    @PostMapping(path = "/update/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> updateImg(@RequestPart("file") MultipartFile file,
//                                       @PathVariable("id") Long imageId) {
//        MultiTypeFile foundedImg = imageService.findById(imageId);
//        if (foundedImg == null) {
//            return new ResponseEntity<>("There is no image with id: " + imageId, HttpStatus.NOT_FOUND);
//        }
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("You must select a file!", HttpStatus.NOT_FOUND);
//        }
//        imageService.updateImage(file, imageId);
//        byte[] imageByte = foundedImg.getData();
//        return ok().contentType(valueOf(foundedImg.getFileType()))
//                .body(imageByte);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteById(@PathVariable("id") Long imageId) {
//        MultiTypeFile foundedImg = imageService.findById(imageId);
//        if (foundedImg != null) {
//            imageService.removeById(imageId);
//            return new ResponseEntity<>("-Successfully removed file with id: " + imageId + "-", new HttpHeaders(),
//                    HttpStatus.OK);
//        }
//        return new ResponseEntity<>("-Not file found with id: " + imageId + "-",HttpStatus.NOT_FOUND);
//    }
//
//}
//
//
////    @GetMapping("/find/{id}")
////    public ResponseEntity<Image> getImg(@PathVariable("id") Long id) {
////        Image image = imageService.findById(id);
////        return ResponseEntity.ok().body(image);
//////                .contentType(MediaType.parseMediaType(image.getFileType()))
//////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + image.getFileName() + "\"")
//////                .body(new ByteArrayResource(image.getData()));
////    }