package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.errorhandler.exception.FileStorageException;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.repository.MultiTypeFileRepository;
import pl.coderslab.service.MultiTypeFileService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class MultiTypeFileServiceImpl implements MultiTypeFileService {

    private static final String UPLOADED_FOLDER = "src/main/resources/META-INF/static/img";
    private static final Path fileStorageLocation = Paths.get(UPLOADED_FOLDER).toAbsolutePath().normalize();

    private final MultiTypeFileRepository fileRepository;

    @Autowired
    public MultiTypeFileServiceImpl(MultiTypeFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public MultiTypeFileDto createFile(MultipartFile file) {
        MultiTypeFile newFile = new MultiTypeFile();
        MultiTypeFileDto savedDto = null;
        try {
            if (!file.isEmpty()) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                if (fileName.contains(".."))
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                newFile.setFileName(fileName);
                newFile.setFileType(file.getContentType());
                newFile.setSize(file.getSize());
                newFile.setData(file.getBytes());
                savedDto = convertToObjectDTO(fileRepository.save(newFile));
                saveFileIntoDir(file);
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return savedDto;
    }

    @Override
    public MultiTypeFileDto findByFileId(Long fileId) {
        return convertToObjectDTO(getFileById(fileId));
    }

    @Override
    public MultiTypeFileDto updateFile(MultipartFile file, Long fileId) throws EntityNotFoundException {
        MultiTypeFile founded = getFileById(fileId);
        MultiTypeFileDto updatedDto = null;
        try {
            if (!file.isEmpty() && founded != null) {
                founded.setFileName(file.getOriginalFilename());
                founded.setFileType(file.getContentType());
                founded.setData(file.getBytes());
                saveFileIntoDir(file);
                updatedDto = convertToObjectDTO(fileRepository.save(founded));
            }
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return updatedDto;
    }

    @Override
    public boolean removeFileById(Long fileId) throws EntityNotFoundException {
        MultiTypeFile founded = getFileById(fileId);
        if (founded != null) {
            fileRepository.delete(founded);
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<?> loadIntoBrowser(Long fileId) throws EntityNotFoundException {
        MultiTypeFileDto foundedFile = convertToObjectDTO(getFileById(fileId));
        if (foundedFile != null) {
            byte[] imageByte = foundedFile.getData();
            return ok().contentType(MediaType.valueOf(foundedFile.getFileType()))
                    .body(imageByte);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> downloadFileById(Long fileId) throws EntityNotFoundException {
        MultiTypeFileDto foundedFile = convertToObjectDTO(getFileById(fileId));
        if (foundedFile != null) {
            byte[] imageByte = foundedFile.getData();
            return ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(imageByte);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public MultiTypeFileDto convertToObjectDTO(MultiTypeFile file) {
        MultiTypeFileDto fileDto = new ModelMapper().map(file, MultiTypeFileDto.class);
        fileDto.setUploadDir(getURLtoFile(file.getId()));
        return fileDto;
    }

    @Override
    public MultiTypeFile convertToEntity(MultiTypeFileDto multiTypeFileDto) {
        return new ModelMapper().map(multiTypeFileDto, MultiTypeFile.class);
    }

    private URL getURLtoFile(Long fileId) throws EntityNotFoundException {
        URL fileURL = null;
        String fileName = getFileById(fileId).getFileName();
        try {
            fileURL = new URL(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/file/showFile/")
                    .path(fileName + "/")
                    .path(String.valueOf(fileId))
                    .toUriString());
        } catch (MalformedURLException e) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return fileURL;
    }

    private static void saveFileIntoDir(MultipartFile file) {
        Path path = Paths.get(fileStorageLocation + "/" + file.getOriginalFilename());
        try {
            file.transferTo(path);
        } catch (IOException e) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private MultiTypeFile getFileById(Long fileId) throws EntityNotFoundException {
        return fileRepository.findById(fileId).orElseThrow(
                () -> new EntityNotFoundException(MultiTypeFile.class, "id", fileId.toString()));
    }

}