package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.EmailMessageDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.service.EmailService;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public boolean sendEmail(@Valid @RequestBody EmailMessageDto emailMessageDto,
                             @RequestParam("recipientEmail") String recipientEmail,
                             @RequestParam("senderEmail") String senderEmail) {
        return emailService.sendEmail(emailMessageDto, recipientEmail, senderEmail);
    }

    @GetMapping("/find/{id}")
    public EmailMessageDto findEmailMessageById(@PathVariable("id") Long emailId) throws EntityNotFoundException {
        return emailService.findById(emailId);
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeEmailById(@PathVariable("id") Long emailId) throws EntityNotFoundException {
        return emailService.removeById(emailId);
    }

}