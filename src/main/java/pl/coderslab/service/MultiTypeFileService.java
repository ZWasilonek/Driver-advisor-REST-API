package pl.coderslab.service;

import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.service.generic.GenericService;

public interface MultiTypeFileService<T> extends GenericService<T> {

    MultiTypeFile findByFileName(String fileName);

    void saveImage(MultipartFile file);

    void updateImage(MultipartFile file, Long imageId);

}
