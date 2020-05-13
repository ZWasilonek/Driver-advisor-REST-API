package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public String createUser(@Valid @RequestBody User user) {
        userService.saveUser(user);
        return "-user created-";
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found for this id: " + userId);
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable(value = "id") Long userId,
                             @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User foundedUser = userService.findById(userId);
        if (foundedUser != null) {
            foundedUser.setUsername(userDetails.getUsername());
            foundedUser.setPassword(userDetails.getPassword());
            userService.saveUser(foundedUser);
            return "-" + foundedUser.toString() + " updated-";
        }
        throw new ResourceNotFoundException("User not found for this id: " + userId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable(value = "id") Long id) {
        userService.removeById(id);
        return "-user with id " + id + " has been removed";
    }

    @GetMapping("/findAll")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

}
