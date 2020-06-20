package pl.coderslab.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.model.MultiTypeFile;

public interface MultiTypeFileService {

    MultiTypeFileDto createFile(MultipartFile file);
    MultiTypeFileDto findByFileId(Long fileId);
    MultiTypeFileDto updateFile(MultipartFile file, Long imageId);
    boolean removeFileById(Long fileId);

    ResponseEntity<?> loadIntoBrowser(Long fileId);
    ResponseEntity<?> downloadFileById(Long fileId);

    MultiTypeFileDto convertToObjectDTO(MultiTypeFile multiTypeFile);
    MultiTypeFile convertToEntity(MultiTypeFileDto multiTypeFileDto);

}