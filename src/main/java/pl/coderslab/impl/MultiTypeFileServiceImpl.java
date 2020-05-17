package pl.coderslab.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.repository.MultiTypeFileRepository;
import pl.coderslab.service.MultiTypeFileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class MultiTypeFileServiceImpl extends GenericServiceImpl<MultiTypeFileDto, MultiTypeFile, MultiTypeFileRepository> implements MultiTypeFileService {

    private final Logger logger = LoggerFactory.getLogger(MultiTypeFileServiceImpl.class);
    String UPLOADED_FOLDER = "/home/zofia/Pulpit/Coderlabs/PortfolioLab/Driver_REST_API/Driving_Advisor/Driving-advisor/src/main/webapp/resources/img/";
    private final Path fileStorageLocation = Paths.get(UPLOADED_FOLDER).toAbsolutePath().normalize();

    @Autowired
    public MultiTypeFileServiceImpl(MultiTypeFileRepository repository) {
        super(repository);
    }

//    public String storeFile(MultipartFile file, Long entityId, String objectType) {
//        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String fileName = "";
//        try {
//            // Check if the file's name contains invalid characters
//            if(originalFileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + originalFileName);
//            }
//            String fileExtension = "";
//            try {
//                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//            } catch(Exception e) {
//                fileExtension = "";
//            }
//
//            try {
//                Files.createDirectories(this.fileStorageLocation);
//            } catch (Exception ex) {
//                throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
//            }
//
//            fileName = entityId + "_" + fileExtension;
//            // Copy file to the target location (Replacing existing file with the same name)
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
////            MultiTypeFile multiTypeFile = getByEntityId(entityId);
//            multiTypeFile.setFileType(file.getContentType());
//            multiTypeFile.setFileName(fileName);
////            setEntityId(objectType, entityId);
//            repository.save(multiTypeFile);
//            return fileName;
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }
//    }

    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    public String getFileName(Long entityId) {
        return getByEntityId(entityId).getFileName();
    }


    @Override
    public MultiTypeFileDto findByFileName(String fileName) {
        return convertToObjectDTO(repository.findByFileName(fileName), MultiTypeFileDto.class);
    }

    @Override
    public MultiTypeFileDto saveFile(MultipartFile file) {
        MultiTypeFileDto multiTypeFileDto = new MultiTypeFileDto();
        try {
            if (!file.isEmpty()) {
                multiTypeFileDto.setFileName(file.getOriginalFilename());
                multiTypeFileDto.setFileType(file.getContentType());
                multiTypeFileDto.setSize(file.getSize());
                multiTypeFileDto.setData(file.getBytes());
                this.create(multiTypeFileDto);
                saveImgIntoDir(file);
                logger.debug("Single file upload!");
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return multiTypeFileDto;
    }

    @Override
    public MultiTypeFileDto updateFile(MultipartFile file, Long fileId) throws EntityNotFoundException {
        MultiTypeFileDto multiTypeFileDto = findById(fileId);
        try {
            if (!file.isEmpty() && multiTypeFileDto != null) {
                multiTypeFileDto.setFileName(file.getOriginalFilename());
                multiTypeFileDto.setFileType(file.getContentType());
                multiTypeFileDto.setData(file.getBytes());
                this.update(multiTypeFileDto);
//                saveImgIntoDir(file);
                logger.debug("Single file upload!");
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return multiTypeFileDto;
    }

    public ResponseEntity<?> upload(Long fileId) throws EntityNotFoundException {
        MultiTypeFileDto foundedFile = findById(fileId);
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

    private MultiTypeFileDto getByEntityId(Long entityId) {
        Set<MultiTypeFile> filesWithNull = new HashSet<>();
        filesWithNull.add(repository.findByAnswerId(entityId));
        filesWithNull.add(repository.findByAdviceId(entityId));
//        filesWithNull.add(repository.findByTrainingId(entityId));
        MultiTypeFile multiTypeFile = filesWithNull.stream()
                .filter(Objects::nonNull).findFirst().orElse(null);
        if (multiTypeFile == null) {
            throw new EntityNotFoundException(MultiTypeFile.class, "id", entityId.toString());
        }
        return convertToObjectDTO(multiTypeFile, MultiTypeFileDto.class);
    }

//    public void setEntityId(String objectType, Long entityId, Long fileId) throws EntityNotFoundException {
//        MultiTypeFile multiTypeFile = new MultiTypeFile();
//        String lowerObjectType = objectType.toLowerCase();
//        if (lowerObjectType.contains("advice")) {
//            multiTypeFile.setAdviceId(entityId);
//            Advice advice = adviceService.findById(entityId);
//            advice.setMultiTypeFileId(fileId);
//            adviceService.update(advice);
//        } else if (lowerObjectType.contains("trainer")) {
//            multiTypeFile.setTrainingId(entityId);
//            Training training = trainingService.findById(entityId);
//            training.setMultiTypeFileId(fileId);
//            trainingService.update(training);
//        } else if (lowerObjectType.contains("answer")) {
//            multiTypeFile.setAnswerId(entityId);
//            Answer answer = answerService.findById(entityId);
//            answer.setMultiTypeFileId(fileId);
//            answerService.update(answer);
//        }
//        this.update(multiTypeFile);
//    }
}
