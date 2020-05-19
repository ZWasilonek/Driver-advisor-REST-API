package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.AnswerDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.AnswerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URL;
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
    public AnswerDto createAnswer(@Valid @RequestBody AnswerDto answerDto) {
        return answerService.create(answerDto);
    }

    @GetMapping("/find/{id}")
    public AnswerDto findAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        return answerService.findById(answerId);
    }

    @PutMapping("/update")
    public AnswerDto update(@RequestBody AnswerDto answerDto) throws EntityNotFoundException {
        return answerService.updateAnswer(answerDto);
    }

    @DeleteMapping("/delete/{id}")
    public void removeAnswerById(@PathVariable("id") Long answerId) throws EntityNotFoundException {
        answerService.removeById(answerId);
    }

    @ApiOperation(value = "View a set of correct answers by question id", response = Set.class)
    @GetMapping("/findCorrect/{id}")
    public Set<AnswerDto> findCorrectAnswersByQuestionId(@PathVariable("id") Long questionId) throws EntityNotFoundException {
        return answerService.getCorrectAnswersByQuestionId(questionId);
    }

    @GetMapping("/getURLtoFile")
    public URL getURLtoFileByAnswerId(HttpServletRequest request, Long answerId) {
        return answerService.getURLForFile(answerId, request);
    }

}
