package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.dto.UserDto;
import pl.coderslab.service.EmailService;
import pl.coderslab.service.UserService;

@RestController
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
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

    @PostMapping("/sendEmail")
    public String sendEmail() {
        UserDto userDto = getUserFromSession();
        userDto.setEmail("smtp.mailtrap.io");
        try {
            emailService.sendEmailToUser(userDto);
        } catch (MailException mailException) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return "Congratulations! Your mail has been send to the user." + userDto.getEmail();
    }

    @RequestMapping("send-mail-attachment")
    public String sendWithAttachment() {
        UserDto userDto = getUserFromSession();
        userDto.setEmail("smtp.mailtrap.io");
        try {
            emailService.sendEmailWithAttachment(userDto);
        } catch (MailException mailException) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return "Congratulations! Your mail has been send to the user.";
    }

}
