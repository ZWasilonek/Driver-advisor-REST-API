package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.QuestionDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.QuestionServiceImpl;
import pl.coderslab.model.Answer;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/question")
public class QuestionRESTController {

    private final QuestionServiceImpl questionService;

    @Autowired
    public QuestionRESTController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public QuestionDto createQuestion(@Valid @RequestBody QuestionDto questionDto,
                                      @Valid @RequestBody Answer answer1,
                                      @Valid @RequestBody Answer answer2,
                                      @Valid @RequestBody Answer answer3) {
        questionDto.setAnswers(new HashSet<Answer>(Arrays.asList(answer1, answer2, answer3)));
        return questionService.create(questionDto);
    }

    @GetMapping("/find/{id}")
    public QuestionDto findQuestionById(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        return questionService.findById(questionId);
    }

    @PutMapping("/update/{id}")
    public QuestionDto updateQuestionById(@PathVariable("id") Long questionId,
                               @Valid @RequestBody Answer answer1,
                               @Valid @RequestBody Answer answer2,
                               @Valid @RequestBody Answer answer3) throws EntityNotFoundException {
        QuestionDto founded = questionService.findById(questionId);
        founded.setAnswers(new HashSet<Answer>(Arrays.asList(answer1, answer2, answer3)));
        return questionService.update(founded);
    }

    @DeleteMapping("/delete/{id}")
    public void removeQuestionById(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        questionService.removeById(questionId);
    }

}
