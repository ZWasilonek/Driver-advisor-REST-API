package pl.coderslab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.UserDto;
import pl.coderslab.service.impl.generic.GenericServiceImpl;
import pl.coderslab.model.User;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl extends GenericServiceImpl<UserDto, User, UserRepository> implements EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(UserRepository repository, JavaMailSender mailSender) {
        super(repository);
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmailToAdmin() {
        SimpleMailMessage mail = new SimpleMailMessage();
        UserDto admin = convertToObjectDTO(repository.findByUsername("admin"), UserDto.class);
        mail.setTo(admin.getEmail());
        mail.setSubject("Testing Mail API");
        mail.setText("MAIL for ADMIN");
        mailSender.send(mail);
    }

    @Override
    public void sendEmailToUser(UserDto userdto) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(userdto.getEmail());
        mail.setSubject("Testing Mail API");
        mail.setText("Hurray ! You have done that dude...");
        mailSender.send(mail);
    }

    @Override
    public void sendEmailWithAttachment(UserDto userDto) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(userDto.getEmail());
            helper.setSubject("Testing Mail API with Attachment");
            helper.setText("Please find the attached document below.");

            ClassPathResource classPathResource = new ClassPathResource("Attachment.pdf");
            helper.addAttachment(classPathResource.getFilename(), classPathResource);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
