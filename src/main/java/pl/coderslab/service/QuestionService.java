package pl.coderslab.service;

import pl.coderslab.model.Question;
import pl.coderslab.service.generic.GenericService;

public interface QuestionService extends GenericService<Question> {

    Question create(Question question);

}
