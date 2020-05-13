package pl.coderslab.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.impl.UserServiceImpl;
import pl.coderslab.model.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
public class HomeController {

    private final UserServiceImpl userService;

    @Autowired
    public HomeController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/fillData")
    public String fillData() {
        String result = "";
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        userService.saveUser(user);
        result += "-created-";

        Image imageAdvice = new Image();
        imageAdvice.setFileName("test.jpg");
        Image imageQuest = new Image();
        imageQuest.setFileName("testQuestion.jpg");

        Tag tag1 = new Tag();
        tag1.setName("bezpieczeństwo");
        Tag tag2 = new Tag();
        tag2.setName("skręt w lewo");

        Answer answer = new Answer();
        answer.setDescription("Test 1 answer description");
        answer.setIsCorrect(true);
        Answer answer2 = new Answer();
        answer2.setDescription("Test 2 answer description");
        answer2.setIsCorrect(false);
        Answer answer3 = new Answer();
        answer3.setDescription("Test 3 answer description");
        answer3.setIsCorrect(false);


        Question question1 = new Question();
        question1.setTitle("Test 1 training's question");
        question1.setAnswers((Set<Answer>) answer);
        question1.setAnswers((Set<Answer>) answer2);
        question1.setAnswers((Set<Answer>) answer3);
        question1.setImage(imageQuest);
//        Question question2 = new Question();
//        question2.setTitle("Test 2 training's question");

        Training training = new Training();
        training.setCreated(LocalDate.now());
        training.setQuestions((Set<Question>) question1);
        training.setUpdated(LocalDate.now());

        //create Advice
        Advice advice = new Advice();
        advice.setAdmin(user);
        advice.setCreated(LocalDate.now());
        advice.setGuide("Test information");
        advice.setImage(imageAdvice);
        advice.setRecommendation(4);
        advice.setShared(2);
        advice.getTags().add(tag1);
        advice.getTags().add(tag2);
        advice.setTraining(training);
        advice.setUpdated(LocalDate.now());

        return result;
    }

}
