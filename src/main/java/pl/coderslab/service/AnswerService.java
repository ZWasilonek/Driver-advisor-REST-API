package pl.coderslab.service;

import pl.coderslab.model.Answer;
import pl.coderslab.service.generic.GenericService;

import java.util.Set;

public interface AnswerService extends GenericService<Answer> {

    Set<Answer> findCorrectAnswersByQuestionId(Long questionId);

}
