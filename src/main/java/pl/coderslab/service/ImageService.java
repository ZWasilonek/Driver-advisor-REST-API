package pl.coderslab.service;

import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.model.Image;
import pl.coderslab.service.generic.GenericService;

public interface ImageService<T> extends GenericService<T> {

    Image findByFileName(String fileName);

    void saveImage(MultipartFile file);

}
