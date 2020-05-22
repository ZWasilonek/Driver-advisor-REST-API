package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.AnswerService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public AnswerDto createAnswer(@Valid @RequestBody AnswerDto answerDto,
                                  @RequestParam(required = false) Long fileId) {
        return answerService.createAnswer(answerDto, fileId);
    }

    @GetMapping("/find/{id}")
    public AnswerDto findAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        return answerService.findAnswerById(answerId);
    }

    @PutMapping("/update")
    public AnswerDto updateAnswer(@Valid @RequestBody AnswerDto answerDto,
                            @RequestParam(required = false) Long fileId) throws EntityNotFoundException {
        return answerService.updateAnswer(answerDto, fileId);
    }

    @DeleteMapping("/delete/{id}")
    public void removeAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        answerService.removeAnswerById(answerId);
    }

    @ApiOperation(value = "View a set of correct answers by question id", response = Set.class)
    @GetMapping("/findCorrect/{id}")
    public Set<AnswerDto> findCorrectAnswersByQuestionId(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        return answerService.getCorrectAnswersByQuestionId(questionId);
    }

}
