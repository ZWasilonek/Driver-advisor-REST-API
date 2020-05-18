package pl.coderslab.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Training;
import pl.coderslab.repository.TrainingRepository;
import pl.coderslab.service.MultiTypeFileService;
import pl.coderslab.service.TrainingService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class TrainingServiceImpl extends GenericServiceImpl<TrainingDto, Training, TrainingRepository> implements TrainingService {

    private final MultiTypeFileService multiTypeFileService;

    @Autowired
    public TrainingServiceImpl(TrainingRepository repository, MultiTypeFileService multiTypeFileService) {
        super(repository);
        this.multiTypeFileService = multiTypeFileService;
    }

    @Override
    public Set<URL> uploadFiles(MultipartFile[] files) {
        Set<URL> urls = new HashSet<>();
        Arrays.stream(files).map(multiTypeFileService::saveFile)
        .forEach(fileDto -> {
                    try {
                        urls.add(new URL(ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/training/showFile/")
                                .path(fileDto.getId().toString()).toUriString()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                });
        return urls;
    }

}
