package pl.coderslab.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Training;
import pl.coderslab.repository.TrainingRepository;
import pl.coderslab.service.TrainingService;

@Service
public class TrainingServiceImpl extends GenericServiceImpl<Training, TrainingRepository> implements TrainingService {

    private final AnswerServiceImpl answerService;

    @Autowired
    public TrainingServiceImpl(TrainingRepository repository, AnswerServiceImpl answerService) {
        super(repository);
        this.answerService = answerService;
    }

}
