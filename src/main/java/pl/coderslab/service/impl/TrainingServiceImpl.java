package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.dto.UserDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.Training;
import pl.coderslab.repository.TrainingRepository;
import pl.coderslab.service.TrainingService;
import pl.coderslab.service.UserService;

import java.util.*;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserService userService;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, UserService userService) {
        this.trainingRepository = trainingRepository;
        this.userService = userService;
    }

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto) {
        trainingDto.setMaxScore(getCorrectAnswers(trainingDto).size());
        return convertToObjectDTO(trainingRepository.save(convertToEntity(trainingDto)));
    }

    @Override
    public TrainingDto findByTrainingId(Long trainingId) throws EntityNotFoundException {
        return convertToObjectDTO(getTrainingById(trainingId));
    }

    @Override
    public TrainingDto updateTraining(TrainingDto trainingDto) throws EntityNotFoundException {
        getTrainingById(trainingDto.getId());
        Training saved = trainingRepository.save(convertToEntity(trainingDto));
        return convertToObjectDTO(saved);
    }

    @Override
    public boolean removeTrainingById(Long trainingId) {
        Training founded = getTrainingById(trainingId);
        if (founded != null) {
            trainingRepository.delete(founded);
            return true;
        }
        return false;
    }

    @Override
    public TrainingDto convertToObjectDTO(Training training) {
        return new ModelMapper().map(training, TrainingDto.class);
    }

    @Override
    public Training convertToEntity(TrainingDto trainingDto) {
        return new ModelMapper().map(trainingDto, Training.class);
    }

    @Override
    public Set<AnswerDto> getCorrectAnswers(TrainingDto trainingDto) {
        Set<AnswerDto> trueAnswers = new HashSet<>();
        trainingDto.getQuestions()
                .forEach(questionDto -> questionDto.getAnswers().stream()
                        .filter(answerDto -> answerDto.getIsCorrect().equals(true))
                        .forEach(trueAnswers::add));
        return trueAnswers;
    }

    @Override
    public TrainingDto sendUserTrainingSolutions(Long userId, TrainingDto solvedTraining) throws EntityNotFoundException {
        Integer score = getCorrectAnswers(solvedTraining).size();
        TrainingDto unchangedTraining = convertToObjectDTO(getTrainingById(solvedTraining.getId()));
        UserDto foundedUser = userService.findById(userId);
        Integer userScore = foundedUser.getScore();
        if (userScore == null) userScore = 0;
        foundedUser.setScore(userScore + score);
        foundedUser.getTraining().add(unchangedTraining);
        userService.update(foundedUser);
        return solvedTraining;
    }

    private Training getTrainingById(Long trainingId) {
        return trainingRepository.findById(trainingId).orElseThrow(
                () -> new EntityNotFoundException(Training.class, "id", trainingId.toString()));
    }

}
