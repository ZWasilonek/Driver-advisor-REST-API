package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.UserDto;
import pl.coderslab.model.EmailMessage;
import pl.coderslab.service.EmailService;
import pl.coderslab.service.UserService;

import javax.validation.Valid;

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
    public boolean sendEmail(@Valid @RequestBody EmailMessage emailMessage,
                            @RequestParam("recipientEmail") String recipientEmail) {
//        UserDto sender = getUserFromSession();
        return emailService.sendEmail(emailMessage, recipientEmail);
    }

}
