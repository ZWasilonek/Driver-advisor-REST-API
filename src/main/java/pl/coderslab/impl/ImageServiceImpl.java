package pl.coderslab.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Image;
import pl.coderslab.repository.ImageRepository;
import pl.coderslab.service.ImageService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageServiceImpl extends GenericServiceImpl<Image, ImageRepository> implements ImageService<Image> {

    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    public ImageServiceImpl(ImageRepository repository) {
        super(repository);
    }

    @Override
    public Image findByFileName(String fileName) {
        return repository.findByFileName(fileName);
    }

    @Override
    public void saveImage(MultipartFile file) {
        String UPLOADED_FOLDER = "/home/zofia/Pulpit/Coderlabs/PortfolioLab/Driver_REST_API/Driving_Advisor/Driving-advisor/src/main/webapp/resources/img/";
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        try {
            Image image = new Image();
            if (!file.isEmpty()) {
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setData(file.getBytes());
//                file.transferTo(path);
                this.create(image);
                logger.debug("Single file upload!");
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
