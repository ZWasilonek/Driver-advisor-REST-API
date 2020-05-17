package pl.coderslab.controller;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.TrainingServiceImpl;
import pl.coderslab.model.Training;

import javax.validation.Valid;

@RestController
@RequestMapping("/training")
public class TrainingRESTController {

    private final TrainingServiceImpl trainingService;

    public TrainingRESTController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public Training createTraining(@Valid @RequestBody Training training) {
        return trainingService.create(training);
    }

    @GetMapping("/find/{id}")
    public Training findTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        return trainingService.findById(trainingId);
    }

    @PutMapping("/update/{id}")
    public Training updateTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        return trainingService.update(trainingService.findById(trainingId));
    }

    @DeleteMapping("/delete/{id}")
    public void removeTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        trainingService.removeById(trainingId);
    }

}
