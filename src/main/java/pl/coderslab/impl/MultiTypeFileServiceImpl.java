package pl.coderslab.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.repository.MultiTypeFileRepository;
import pl.coderslab.service.MultiTypeFileService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class MultiTypeFileServiceImpl extends GenericServiceImpl<MultiTypeFile, MultiTypeFileRepository> implements MultiTypeFileService {

    private final Logger logger = LoggerFactory.getLogger(MultiTypeFileServiceImpl.class);

    public MultiTypeFileServiceImpl(MultiTypeFileRepository repository) {
        super(repository);
    }

    @Override
    public MultiTypeFile findByFileName(String fileName) {
        return repository.findByFileName(fileName);
    }

    @Override
    public MultiTypeFile saveFile(MultipartFile file) {
        MultiTypeFile multitypeFile = new MultiTypeFile();
        try {
            if (!file.isEmpty()) {
                multitypeFile.setFileName(file.getOriginalFilename());
                multitypeFile.setFileType(file.getContentType());
                multitypeFile.setData(file.getBytes());
                this.create(multitypeFile);
//                saveImgIntoDir(file);
                logger.debug("Single file upload!");
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return multitypeFile;
    }

    @Override
    public MultiTypeFile updateFile(MultipartFile file, Long fileId) throws EntityNotFoundException {
        MultiTypeFile multiTypeFile = findById(fileId);
        try {
            if (!file.isEmpty() && multiTypeFile != null) {
                multiTypeFile.setFileName(file.getOriginalFilename());
                multiTypeFile.setFileType(file.getContentType());
                multiTypeFile.setData(file.getBytes());
                this.update(multiTypeFile);
//                saveImgIntoDir(file);
                logger.debug("Single file upload!");
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return multiTypeFile;
    }

    public ResponseEntity<?> upload(Long fileId) throws EntityNotFoundException {
        MultiTypeFile foundedFile = findById(fileId);
        if (foundedFile != null) {
            byte[] imageByte = foundedFile.getData();
            return ok().contentType(MediaType.valueOf(foundedFile.getFileType()))
                    .body(imageByte);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
