package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.AnswerServiceImpl;
import pl.coderslab.model.Answer;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/answer")
public class AnswerRESTController {

    private final AnswerServiceImpl answerService;

    @Autowired
    public AnswerRESTController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/create")
    public Answer createAnswer(@Valid @RequestBody Answer answer) {
        return answerService.create(answer);
    }

    @GetMapping("/find'{id}")
    public Answer findAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        return answerService.findById(answerId);
    }

    @PutMapping("/update/{id}")
    public Answer updateById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        return answerService.update(answerService.findById(answerId));
    }

    @DeleteMapping("/delete/{id}")
    public void removeAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        answerService.removeById(answerId);
    }

    @GetMapping("/findCorrect/{questionId}")
    public Set<Answer> findCorrectAnswersByQuestionId(@PathVariable("questionId") Long questionId) throws EntityNotFoundException {
        return answerService.findCorrectAnswersByQuestionId(questionId);
    }

}
