package pl.coderslab.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.dto.MultiTypeFileDto;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.service.generic.GenericService;

public interface MultiTypeFileService extends GenericService<MultiTypeFileDto, MultiTypeFile> {

    MultiTypeFileDto findByFileName(String fileName);

    MultiTypeFileDto saveFile(MultipartFile file);

    MultiTypeFileDto updateFile(MultipartFile file, Long imageId);

    ResponseEntity<?> loadIntoBrowser(Long fileId);

    Resource loadFileAsResource(Long fileId);

}
