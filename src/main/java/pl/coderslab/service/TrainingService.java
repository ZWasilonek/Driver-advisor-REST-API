package pl.coderslab.service;

import pl.coderslab.dto.AnswerDto;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.model.Training;

import java.util.Set;

public interface TrainingService {

    TrainingDto createTraining(TrainingDto trainingDto);
    TrainingDto findByTrainingId(Long trainingId);
    TrainingDto updateTraining(TrainingDto trainingDto);
    boolean removeTrainingById(Long trainingId);

    TrainingDto convertToObjectDTO(Training training);
    Training convertToEntity(TrainingDto trainingDto);
    Set<AnswerDto> getCorrectAnswers(TrainingDto trainingDto);
    TrainingDto sendUserTrainingSolutions(Long userId, TrainingDto solvedTraining);

}
