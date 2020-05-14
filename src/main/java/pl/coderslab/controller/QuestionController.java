package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.impl.QuestionServiceImpl;
import pl.coderslab.model.Answer;
import pl.coderslab.model.Question;

import javax.validation.Valid;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionServiceImpl questionService;

    @Autowired
    public QuestionController(QuestionServiceImpl questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody Question question,
                                            @Valid @RequestBody Answer answer1,
                                            @Valid @RequestBody Answer answer2,
                                            @Valid @RequestBody Answer answer3) {
        question.setAnswers(new HashSet<Answer>(Arrays.asList(answer1, answer2, answer3)));
        questionService.create(question);
        return new ResponseEntity<>("-Successfully created-", new HttpHeaders(),
                HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long questionId) {
        Question question = questionService.findById(questionId);
        if (question == null) {
            return new ResponseEntity<>("-Not question found with id: " + questionId + "-",HttpStatus.NOT_FOUND);
        }
        return ok().body(question);
    }
}
