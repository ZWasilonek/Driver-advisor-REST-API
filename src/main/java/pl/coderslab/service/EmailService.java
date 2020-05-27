package pl.coderslab.service;

import pl.coderslab.dto.UserDto;

public interface EmailService {

    void sendEmailToUser(UserDto userDto);
    void sendEmailWithAttachment(UserDto userDto);
    void sendEmailToAdmin();

}
