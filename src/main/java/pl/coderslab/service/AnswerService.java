package pl.coderslab.service;

import pl.coderslab.dto.AnswerDto;
import pl.coderslab.model.Answer;

import java.util.Set;

public interface AnswerService {

    AnswerDto createAnswer(AnswerDto answerDto, Long fileId);
    AnswerDto findAnswerById(Long answerId);
    AnswerDto updateAnswer(AnswerDto answerDto, Long fileId);
    boolean removeAnswerById(Long answerId);

    AnswerDto convertToObjectDTO(Answer answer);
    Answer convertToEntity(AnswerDto answerDto);
    Set<AnswerDto> getCorrectAnswersByQuestionId(Long questionId);

}
