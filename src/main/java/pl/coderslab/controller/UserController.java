package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.UserServiceImpl;
import pl.coderslab.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/find/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) throws EntityNotFoundException {
        return userService.findById(userId);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable(value = "id") Long userId,
                             @Valid @RequestBody User userDetails) throws EntityNotFoundException {
        User foundedUser = userService.findById(userId);
        if (foundedUser != null) {
            foundedUser.setUsername(userDetails.getUsername());
            foundedUser.setPassword(userDetails.getPassword());
            return userService.saveUser(foundedUser);
        }
        throw new ResourceNotFoundException("User not found for this id: " + userId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        userService.removeById(id);
    }

    @GetMapping("/findAll")
    public List<User> getAllUsers() throws EntityNotFoundException {
        return userService.findAll();
    }

}
