package pl.coderslab.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.dto.UserDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Training;
import pl.coderslab.repository.TrainingRepository;
import pl.coderslab.service.TrainingService;
import pl.coderslab.service.UserService;

import java.util.*;

@Service
public class TrainingServiceImpl extends GenericServiceImpl<TrainingDto, Training, TrainingRepository> implements TrainingService {

    private final UserService userService;

    @Autowired
    public TrainingServiceImpl(TrainingRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto) {
        trainingDto.setMaxScore(getCorrectAnswers(trainingDto).size());
        return this.create(trainingDto);
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
    public TrainingDto sentUserTrainingSolutions(Long userId, TrainingDto solvedTraining) throws EntityNotFoundException {
        Integer score = getCorrectAnswers(solvedTraining).size();
        TrainingDto unchangedTraining = this.findById(solvedTraining.getId());
        UserDto foundedUser = userService.findById(userId);
        Integer userScore = foundedUser.getScore();
        if (userScore == null) userScore = 0;
        foundedUser.setScore(userScore + score);
        foundedUser.getTraining().add(unchangedTraining);
        userService.update(foundedUser);
        return solvedTraining;
    }

}
