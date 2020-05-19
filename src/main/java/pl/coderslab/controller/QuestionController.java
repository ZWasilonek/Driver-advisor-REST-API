package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.dto.QuestionDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.AnswerService;
import pl.coderslab.service.QuestionService;

import javax.validation.Valid;

import java.util.Set;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping("/create")
    public QuestionDto createQuestion(@Valid @RequestBody QuestionDto questionDto) {
        return questionService.create(questionDto);
    }

    @GetMapping("/find/{id}")
    public QuestionDto findQuestionById(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        return questionService.findById(questionId);
    }

    @PutMapping("/update")
    public QuestionDto updateQuestionById(@RequestBody QuestionDto questionDto) throws EntityNotFoundException {
        return questionService.update(questionDto);
    }

    @DeleteMapping("/delete/{id}")
    public void removeQuestionById(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        questionService.removeById(questionId);
    }

    @ApiOperation(value = "View a set of correct answers by question id", response = Set.class)
    @GetMapping("/findCorrect/{id}")
    public Set<AnswerDto> findCorrectAnswersByQuestionId(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        return answerService.getCorrectAnswersByQuestionId(questionId);
    }

}
