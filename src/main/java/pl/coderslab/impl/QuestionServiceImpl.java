package pl.coderslab.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Question;
import pl.coderslab.repository.QuestionRepository;
import pl.coderslab.service.QuestionService;

@Slf4j
@Service
public class QuestionServiceImpl extends GenericServiceImpl<Question, QuestionRepository> implements QuestionService {

    public QuestionServiceImpl(QuestionRepository repository) {
        super(repository);
    }

    @Override
    public Question create(Question question) {
        if (question != null && question.getAnswers() != null) {
            this.create(question);
            log.debug("Question created!");
        }
        return question;
    }

}
