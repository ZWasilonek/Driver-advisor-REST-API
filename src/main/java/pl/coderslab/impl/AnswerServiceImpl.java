package pl.coderslab.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Answer;
import pl.coderslab.repository.AnswerRepository;
import pl.coderslab.service.AnswerService;

import java.util.Set;

@Service
public class AnswerServiceImpl extends GenericServiceImpl<Answer, AnswerRepository> implements AnswerService {

    public AnswerServiceImpl(AnswerRepository repository) {
        super(repository);
    }

    @Override
    public Set<Answer> findCorrectAnswersByQuestionId(Long questionId) throws EntityNotFoundException {
        return this.findCorrectAnswersByQuestionId(questionId);
    }

}
