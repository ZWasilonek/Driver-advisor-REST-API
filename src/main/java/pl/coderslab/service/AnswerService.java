package pl.coderslab.service;

import pl.coderslab.dto.AnswerDto;
import pl.coderslab.model.Answer;
import pl.coderslab.service.generic.GenericService;

import java.util.Set;

public interface AnswerService extends GenericService<AnswerDto, Answer> {

    Set<AnswerDto> getCorrectAnswersByQuestionId(Long questionId);

}
