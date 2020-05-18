package pl.coderslab.service;


import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.model.Training;
import pl.coderslab.service.generic.GenericService;

import java.net.URL;
import java.util.Set;

public interface TrainingService extends GenericService<TrainingDto, Training> {

    Set<URL> uploadFiles(MultipartFile[] files);

}
