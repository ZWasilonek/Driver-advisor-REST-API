package pl.coderslab.service;

import pl.coderslab.dto.AnswerDto;
import pl.coderslab.model.Answer;

public interface AnswerService {

    AnswerDto createAnswer(AnswerDto answerDto, Long fileId);
    AnswerDto findAnswerById(Long answerId);
    AnswerDto updateAnswer(AnswerDto answerDto, Long fileId);
    void removeAnswerById(Long answerId);

    AnswerDto convertToObjectDTO(Answer answer);
    Answer convertToEntity(AnswerDto answerDto);

}
