package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.UserDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.impl.UserServiceImpl;

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
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping("/find/{id}")
    public UserDto getUserById(@PathVariable(value = "id") Long userId) throws EntityNotFoundException {
        return userService.findById(userId);
    }

    @PutMapping("/update")
    public UserDto updateUser(@Valid @RequestBody UserDto userDetails) throws EntityNotFoundException {
        return userService.update(userDetails);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        userService.removeById(id);
    }

    @GetMapping("/findAll")
    public List<UserDto> getAllUsers() throws EntityNotFoundException {
        return userService.findAll();
    }

}
