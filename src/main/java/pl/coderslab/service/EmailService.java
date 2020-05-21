package pl.coderslab.service;

import pl.coderslab.dto.UserDto;
import pl.coderslab.model.User;
import pl.coderslab.service.generic.GenericService;

public interface EmailService extends GenericService<UserDto, User> {

    void sendEmailToUser(UserDto userDto);
    void sendEmailWithAttachment(UserDto userDto);
    void sendEmailToAdmin();

}
