package pl.coderslab.service;

import pl.coderslab.dto.UserDto;
import pl.coderslab.model.User;

public interface UserService {

    UserDto findByUserName(String name);

    UserDto saveUser(UserDto user);
    UserDto findUserById(Long userId);
    UserDto updateUser(UserDto userDto);
    boolean removeUserById(Long userId);

    UserDto convertToObjectDTO(User user);
    User convertToEntity(UserDto userDto);

}
