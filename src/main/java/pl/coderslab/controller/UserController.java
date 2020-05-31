package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.UserDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping("/find/{id}")
    public UserDto getUserById(@PathVariable(value = "id") Long userId) throws EntityNotFoundException {
        return userService.findUserById(userId);
    }

    @PutMapping("/update")
    public UserDto updateUser(@Valid @RequestBody UserDto userDetails) throws EntityNotFoundException {
        return userService.updateUser(userDetails);
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeUserById(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        return userService.removeUserById(id);
    }

}
