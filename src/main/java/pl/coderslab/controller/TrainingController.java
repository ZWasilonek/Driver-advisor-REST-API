package pl.coderslab.controller;

import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.TrainingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
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

    @PostMapping("/solveTraining/{id}")
    public TrainingDto sendUserTrainingSolutions(@PathVariable("id") Long userId,
                                             @RequestBody TrainingDto trainingDto) throws EntityNotFoundException {
        return trainingService.sentUserTrainingSolutions(userId, trainingDto);
    }

}
