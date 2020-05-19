package pl.coderslab.service;

import pl.coderslab.dto.AnswerDto;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.model.Training;
import pl.coderslab.service.generic.GenericService;

import java.util.Set;

public interface TrainingService extends GenericService<TrainingDto, Training> {

    TrainingDto createTraining(TrainingDto trainingDto);
    Set<AnswerDto> getCorrectAnswers(TrainingDto trainingDto);
    TrainingDto sentUserTrainingSolutions(Long userId, TrainingDto solvedTraining);
}
