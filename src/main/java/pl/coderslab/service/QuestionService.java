package pl.coderslab.service;

import pl.coderslab.dto.QuestionDto;
import pl.coderslab.model.Question;
import pl.coderslab.service.generic.GenericService;

public interface QuestionService extends GenericService<QuestionDto, Question> {

    QuestionDto create(QuestionDto question);

}
