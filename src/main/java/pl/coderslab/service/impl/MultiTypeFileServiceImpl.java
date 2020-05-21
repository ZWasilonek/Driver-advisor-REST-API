package pl.coderslab.service.impl;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
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
import pl.coderslab.errorhandler.exception.FileStorageException;
import pl.coderslab.service.impl.generic.GenericServiceImpl;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.repository.MultiTypeFileRepository;
import pl.coderslab.service.MultiTypeFileService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class MultiTypeFileServiceImpl extends GenericServiceImpl<MultiTypeFileDto, MultiTypeFile, MultiTypeFileRepository> implements MultiTypeFileService {

    String UPLOADED_FOLDER = "/home/zofia/Pulpit/Coderlabs/PortfolioLab/Driver_REST_API/Driving_Advisor/Driving-advisor/src/main/webapp/resources/img/";
    private final Path fileStorageLocation = Paths.get(UPLOADED_FOLDER).toAbsolutePath().normalize();

    @Autowired
    public MultiTypeFileServiceImpl(MultiTypeFileRepository repository) {
        super(repository);
    }

    // DO ściągania z url - nie działa
    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    //???
    public String getFileName(Long entityId) {
        return getByEntityId(entityId).getFileName();
    }

    //do naprawy
    public void saveFromURL(String url) {
//        try {
//            URL fileURL = new URL(url);
//            try (InputStream in = fileURL.openStream()) {
//                Files.copy(in, fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

        try {
            URL fileURL = new URL(url);
            File file = new File(fileStorageLocation.toString());
            FileUtils.copyURLToFile(fileURL, file);
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MultiTypeFileDto findByFileName(String fileName) {
        return convertToObjectDTO(repository.findByFileName(fileName), MultiTypeFileDto.class);
    }

    @Override
    public MultiTypeFileDto saveFile(MultipartFile file) {
        MultiTypeFileDto multiTypeFileDto = new MultiTypeFileDto();
        MultiTypeFileDto savedDto = null;
        try {
            if (!file.isEmpty()) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                if (fileName.contains(".."))
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                multiTypeFileDto.setFileName(fileName);
                multiTypeFileDto.setFileType(file.getContentType());
                multiTypeFileDto.setSize(file.getSize());
                multiTypeFileDto.setData(file.getBytes());
//                multiTypeFileDto.setUploadDir();
                savedDto = this.create(multiTypeFileDto);
//                saveImgIntoDir(file);
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return savedDto;
    }

    @Override
    public MultiTypeFileDto updateFile(MultipartFile file, Long fileId) throws EntityNotFoundException {
        MultiTypeFileDto multiTypeFileDto = findById(fileId);
        MultiTypeFileDto updatedDto = null;
        try {
            if (!file.isEmpty() && multiTypeFileDto != null) {
                multiTypeFileDto.setFileName(file.getOriginalFilename());
                multiTypeFileDto.setFileType(file.getContentType());
                multiTypeFileDto.setData(file.getBytes());
                updatedDto = this.update(multiTypeFileDto);
//                saveImgIntoDir(file);
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return updatedDto;
    }

    @Override
    public ResponseEntity<?> loadIntoBrowser(Long fileId) throws EntityNotFoundException {
        MultiTypeFileDto foundedFile = findById(fileId);
        if (foundedFile != null) {
            byte[] imageByte = foundedFile.getData();
            return ok().contentType(MediaType.valueOf(foundedFile.getFileType()))
                    .body(imageByte);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Do naprawy
    public ResponseEntity<?> uploadFromURL(Long fileId, URL url) throws EntityNotFoundException {
        MultiTypeFileDto foundedFile = findById(fileId);
        if (foundedFile != null) {
            byte[] imageByte = foundedFile.getData();
            return ok().contentType(MediaType.valueOf(foundedFile.getFileType()))
                    .body(imageByte);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Resource loadFileAsResource(Long fileId) {
        MultiTypeFileDto fileDto = this.findById(fileId);
        Resource resource = null;
        try {
            Path filePath = this.fileStorageLocation.resolve(fileDto.getFileName()).normalize();
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resource;
    }

    private void saveImgIntoDir(MultipartFile file) {
        String UPLOADED_FOLDER = "/home/zofia/Pulpit/Coderlabs/PortfolioLab/Driver_REST_API/Driving_Advisor/Driving-advisor/src/main/webapp/resources/img/";
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
        try {
            file.transferTo(path);
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
