package pl.coderslab.service;

import pl.coderslab.dto.QuestionDto;
import pl.coderslab.model.Question;

public interface QuestionService {

    QuestionDto createQuestion(QuestionDto questionDto);
    QuestionDto findQuestionById(Long questionId);
    QuestionDto updateQuestion(QuestionDto questionDto);
    void removeQuestionById(Long questionId);

    QuestionDto convertToObjectDTO(Question question);
    Question convertToEntity(QuestionDto questionDto);

}