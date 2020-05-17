package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.AnswerServiceImpl;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/answer")
public class AnswerRESTController {

    private final AnswerServiceImpl answerService;

    @Autowired
    public AnswerRESTController(AnswerServiceImpl answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public AnswerDto createAnswer(@Valid @RequestBody AnswerDto answerDto) {
        return answerService.create(answerDto);
    }

    @GetMapping("/find/{id}")
    public AnswerDto findAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        return answerService.findById(answerId);
    }

    @PutMapping("/update/{id}")
    public AnswerDto updateById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        return answerService.update(answerService.findById(answerId));
    }

    @DeleteMapping("/delete/{id}")
    public void removeAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        answerService.removeById(answerId);
    }

    @GetMapping("/findCorrect/{questionId}")
    public Set<AnswerDto> findCorrectAnswersByQuestionId(@PathVariable("questionId") Long questionId) throws EntityNotFoundException {
        return answerService.getCorrectAnswersByQuestionId(questionId);
    }

}
