package pl.coderslab.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.repository.MultiTypeFileRepository;
import pl.coderslab.service.MultiTypeFileService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MultiTypeFileServiceImpl extends GenericServiceImpl<MultiTypeFile, MultiTypeFileRepository> implements MultiTypeFileService<MultiTypeFile> {

    private final Logger logger = LoggerFactory.getLogger(MultiTypeFileServiceImpl.class);

    public MultiTypeFileServiceImpl(MultiTypeFileRepository repository) {
        super(repository);
    }

    @Override
    public MultiTypeFile findByFileName(String fileName) {
        return repository.findByFileName(fileName);
    }

    @Override
    public void saveImage(MultipartFile file) {
        try {
            MultiTypeFile multitypeFile = new MultiTypeFile();
            if (!file.isEmpty()) {
                multitypeFile.setFileName(file.getOriginalFilename());
                multitypeFile.setFileType(file.getContentType());
                multitypeFile.setData(file.getBytes());
                this.create(multitypeFile);
//                saveImgIntoDir(file);
                logger.debug("Single file upload!");
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //zle obsługujesz wyjątki
            //amazon
        }
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        MultiTypeFile multitypeFile = repository.findById(imageId).orElse(null);
        try {
            if (!file.isEmpty() && multitypeFile != null) {
                multitypeFile.setFileName(file.getOriginalFilename());
                multitypeFile.setFileType(file.getContentType());
                multitypeFile.setData(file.getBytes());
                this.update(multitypeFile);
//                saveImgIntoDir(file);
                logger.debug("Single file upload!");
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void saveImgIntoDir(MultipartFile file) {
        String UPLOADED_FOLDER = "/home/zofia/Pulpit/Coderlabs/PortfolioLab/Driver_REST_API/Driving_Advisor/Driving-advisor/src/main/webapp/resources/img/";
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        try {
            file.transferTo(path);
            logger.debug("file saved into the path");
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
