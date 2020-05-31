package pl.coderslab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.coderslab.model.EmailMessage;
import pl.coderslab.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sendEmail(final EmailMessage email, String recipientEmail) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(email.getSubject());
        mail.setTo(recipientEmail);
        mail.setText(email.getMessage());
        try {
            mailSender.send(mail);
            return true;
        } catch (MailException e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return false;
    }

}
