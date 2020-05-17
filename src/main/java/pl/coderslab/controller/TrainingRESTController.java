package pl.coderslab.controller;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.TrainingServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/training")
public class TrainingRESTController {

    private final TrainingServiceImpl trainingService;

    public TrainingRESTController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public TrainingDto createTraining(@Valid @RequestBody TrainingDto trainingDto) {
        return trainingService.create(trainingDto);
    }

    @GetMapping("/find/{id}")
    public TrainingDto findTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        return trainingService.findById(trainingId);
    }

    @PutMapping("/update/{id}")
    public TrainingDto updateTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        return trainingService.update(trainingService.findById(trainingId));
    }

    @DeleteMapping("/delete/{id}")
    public void removeTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        trainingService.removeById(trainingId);
    }

}
