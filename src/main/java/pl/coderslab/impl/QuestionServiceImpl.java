package pl.coderslab.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Question;
import pl.coderslab.repository.QuestionRepository;
import pl.coderslab.service.QuestionService;

@Service
public class QuestionServiceImpl extends GenericServiceImpl<Question, QuestionRepository> implements QuestionService {

    private final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    public QuestionServiceImpl(QuestionRepository repository) {
        super(repository);
    }

    @Override
    public void create(Question question) {
        if (question != null && question.getAnswers() != null) {
            this.create(question);
            logger.debug("Question created!");
        }
        new ResponseEntity<>("-Failed to create a question-", HttpStatus.NOT_FOUND);
    }
}
