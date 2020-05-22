package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.dto.QuestionDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.Advice;
import pl.coderslab.model.Answer;
import pl.coderslab.model.Question;
import pl.coderslab.repository.QuestionRepository;
import pl.coderslab.service.AnswerService;
import pl.coderslab.service.QuestionService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    @Override
    public QuestionDto createQuestion(QuestionDto questionDto) {
        Question question = convertToEntity(questionDto);
        questionRepository.save(question);
        return convertToObjectDTO(question);
    }

    @Override
    public QuestionDto findQuestionById(Long questionId) throws EntityNotFoundException {
        return convertToObjectDTO(getQuestionById(questionId));
    }

    @Override
    public QuestionDto updateQuestion(QuestionDto questionDto) throws EntityNotFoundException {
        checkIfQuestionExistsOrThrowException(questionDto.getId());
        return convertToObjectDTO(questionRepository.save(convertToEntity(questionDto)));
    }

    @Override
    public void removeQuestionById(Long questionId) throws EntityNotFoundException {
        checkIfQuestionExistsOrThrowException(questionId);
        questionRepository.deleteById(questionId);
    }

    @Override
    public QuestionDto convertToObjectDTO(Question question) {
        Set<AnswerDto> answersWithURLToFile = question.getAnswers().stream().map(answerService::convertToObjectDTO)
                .collect(Collectors.toSet());
        QuestionDto result = new ModelMapper().map(question, QuestionDto.class);
        result.setAnswers(answersWithURLToFile);
        return result;
    }

    @Override
    public Question convertToEntity(QuestionDto questionDto) {
        Set<Answer> answersWithFileId = questionDto.getAnswers().stream()
                .map(answerService::convertToEntity)
                .collect(Collectors.toSet());
        Question result = new ModelMapper().map(questionDto, Question.class);
        result.setAnswers(answersWithFileId);
        return result;
    }

    private void checkIfQuestionExistsOrThrowException(Long questionId) throws EntityNotFoundException {
        questionRepository.findById(questionId).orElseThrow(
                () -> new EntityNotFoundException(Advice.class, "id", questionId.toString()));
    }

    private Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(
                () -> new EntityNotFoundException(Advice.class, "id", questionId.toString()));
    }

}
