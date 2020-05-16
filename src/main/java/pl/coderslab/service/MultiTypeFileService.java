package pl.coderslab.service;

import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.model.MultiTypeFile;
import pl.coderslab.service.generic.GenericService;

public interface MultiTypeFileService extends GenericService<MultiTypeFile> {

    MultiTypeFile findByFileName(String fileName);

    MultiTypeFile saveFile(MultipartFile file);

    MultiTypeFile updateFile(MultipartFile file, Long imageId);

}
