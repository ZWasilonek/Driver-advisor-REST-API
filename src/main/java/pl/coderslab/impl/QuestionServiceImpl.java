package pl.coderslab.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.dto.QuestionDto;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Question;
import pl.coderslab.repository.QuestionRepository;
import pl.coderslab.service.QuestionService;

@Service
public class QuestionServiceImpl extends GenericServiceImpl<QuestionDto, Question, QuestionRepository> implements QuestionService {

    public QuestionServiceImpl(QuestionRepository repository) {
        super(repository);
    }

}
