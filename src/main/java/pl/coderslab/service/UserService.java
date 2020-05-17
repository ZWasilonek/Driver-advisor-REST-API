package pl.coderslab.service;

import pl.coderslab.dto.UserDto;
import pl.coderslab.model.User;
import pl.coderslab.service.generic.GenericService;

public interface UserService extends GenericService<UserDto, User> {
    UserDto findByUserName(String name);

    UserDto saveUser(UserDto user);
}
