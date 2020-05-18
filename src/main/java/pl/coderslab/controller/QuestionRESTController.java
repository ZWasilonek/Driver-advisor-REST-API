package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.QuestionDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.QuestionServiceImpl;
import pl.coderslab.model.Answer;
import pl.coderslab.service.QuestionService;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/question")
public class QuestionRESTController {

    private final QuestionService questionService;

    @Autowired
    public QuestionRESTController(QuestionService questionService) {
        this.questionService = questionService;
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



}
