package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.QuestionServiceImpl;
import pl.coderslab.model.Answer;
import pl.coderslab.model.Question;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionServiceImpl questionService;

    @Autowired
    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public Question createQuestion(@Valid @RequestBody Question question,
                                            @Valid @RequestBody Answer answer1,
                                            @Valid @RequestBody Answer answer2,
                                            @Valid @RequestBody Answer answer3) {
        question.setAnswers(new HashSet<Answer>(Arrays.asList(answer1, answer2, answer3)));
        return questionService.create(question);
    }

    @GetMapping("/find/{id}")
    public Question findById(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        return questionService.findById(questionId);
    }

    @PutMapping("/update/{id}")
    public Question updateById(@PathVariable("id") Long questionId,
                               @Valid @RequestBody Answer answer1,
                               @Valid @RequestBody Answer answer2,
                               @Valid @RequestBody Answer answer3) throws EntityNotFoundException {
        Question founded = questionService.findById(questionId);
        founded.setAnswers(new HashSet<Answer>(Arrays.asList(answer1, answer2, answer3)));
        return questionService.update(founded);
    }

    @DeleteMapping("/delete/{id}")
    public void removeById(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        questionService.removeById(questionId);
    }

}
