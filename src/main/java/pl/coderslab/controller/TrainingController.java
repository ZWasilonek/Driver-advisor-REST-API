package pl.coderslab.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.TrainingDto;
import pl.coderslab.dto.UserDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.TrainingService;
import pl.coderslab.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService;
    private final UserService userService;

    @Autowired
    public TrainingController(TrainingService trainingService, UserService userService) {
        this.trainingService = trainingService;
        this.userService = userService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public TrainingDto createTraining(@Valid @RequestBody TrainingDto trainingDto) {
        return trainingService.createTraining(trainingDto);
    }

    @GetMapping("/find/{id}")
    public TrainingDto findTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        return trainingService.findByTrainingId(trainingId);
    }

    @PutMapping("/update")
    public TrainingDto updateTraining(@Valid @RequestBody TrainingDto trainingDto) throws EntityNotFoundException {
        return trainingService.updateTraining(trainingDto);
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeTrainingById(@PathVariable("id") Long trainingId) throws EntityNotFoundException {
        return trainingService.removeTrainingById(trainingId);
    }

    @ModelAttribute("userSession")
    public UserDto getUserFromSession() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.findByUserName(username);
        }
        return null;
    }

    @ApiOperation(value = "Assigns the training and score (number of correct answers) to the user found in session and return the same previously sent object TrainingDto", response = TrainingDto.class)
    @PostMapping("/solveTraining")
    public TrainingDto sendUserTrainingSolutions(@Valid @RequestBody TrainingDto trainingDto) throws EntityNotFoundException {
        return trainingService.sendUserTrainingSolutions(getUserFromSession().getId(), trainingDto);
    }

}
