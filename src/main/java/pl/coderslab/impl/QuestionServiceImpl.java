package pl.coderslab.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.QuestionDto;
import pl.coderslab.impl.generic.GenericServiceImpl;
import pl.coderslab.model.Question;
import pl.coderslab.repository.QuestionRepository;
import pl.coderslab.service.QuestionService;

@Slf4j
@Service
public class QuestionServiceImpl extends GenericServiceImpl<QuestionDto, Question, QuestionRepository> implements QuestionService {

    public QuestionServiceImpl(QuestionRepository repository) {
        super(repository);
    }

    @Override
    public QuestionDto create(QuestionDto questionDto) {
        if (questionDto != null && questionDto.getAnswers() != null) {
            this.create(questionDto);
            log.debug("Question created!");
        }
        return questionDto;
    }

}
