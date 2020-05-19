package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.MultiTypeFileService;
import pl.coderslab.service.TrainingService;

import javax.validation.Valid;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService;
    private final MultiTypeFileService multiTypeFileService;

    public TrainingController(TrainingService trainingService, MultiTypeFileService multiTypeFileService) {
        this.trainingService = trainingService;
        this.multiTypeFileService = multiTypeFileService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public TrainingDto createTraining(@Valid @RequestBody TrainingDto trainingDto) {
        return trainingService.createTraining(trainingDto);
    }

    @GetMapping("/find/{id}")
    public TrainingDto findTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        return trainingService.findById(trainingId);
    }

    @PutMapping("/update")
    public TrainingDto updateTraining(@RequestBody TrainingDto trainingDto) throws EntityNotFoundException {
        return trainingService.update(trainingDto);
    }

    @DeleteMapping("/delete/{id}")
    public void removeTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        trainingService.removeById(trainingId);
    }

    //NIE WCZYTUJE PLIKÓW
    @ApiOperation(value = "Upload files and receive list of urls for view", response = URL.class)
    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public Set<URL> uploadFile(@RequestParam("files") MultipartFile[] files) {
        return trainingService.uploadFiles(files);
    }

    @ApiOperation(value = "Display a file by file id from URL in browser", response = URL.class)
    @GetMapping("/showFile/{id}")
    public ResponseEntity<?> displayById(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.loadIntoBrowser(fileId);
    }

}
