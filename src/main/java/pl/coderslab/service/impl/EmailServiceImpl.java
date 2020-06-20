package pl.coderslab.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.EmailMessageDto;
import pl.coderslab.dto.UserDto;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.model.EmailMessage;
import pl.coderslab.repository.EmailRepository;
import pl.coderslab.service.EmailService;
import pl.coderslab.service.UserService;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final UserService userService;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, EmailRepository emailRepository, UserService userService) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
        this.userService = userService;
    }

    @Override
    public boolean sendEmail(final EmailMessageDto emailDto, String recipientEmail, String senderEmail) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(emailDto.getSubject());
        mail.setTo(recipientEmail);
        mail.setText(emailDto.getMessage());

        UserDto recipient = findRecipientInDatabase(recipientEmail);
        if (recipient != null) {
            emailDto.setRecipient(recipient);
        }
        UserDto sender = findRecipientInDatabase(senderEmail);
        if (sender != null) {
            emailDto.setSender(sender);
        }

        try {
            mailSender.send(mail);
            saveEmail(emailDto);
            return true;
        } catch (MailException e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return false;
    }

    @Override
    public void saveEmail(EmailMessageDto emailDto) {
        emailRepository.save(convertToEntity(emailDto));
    }

    @Override
    public EmailMessageDto findById(Long emailId) throws EntityNotFoundException {
        return convertToObjectDTO(getEmailByIdOrThrowException(emailId));
    }

    @Override
    public boolean removeById(Long emailId) throws EntityNotFoundException {
        EmailMessage founded = getEmailByIdOrThrowException(emailId);
        emailRepository.delete(founded);
        return true;
    }

    @Override
    public EmailMessageDto convertToObjectDTO(EmailMessage email) {
        return new ModelMapper().map(email, EmailMessageDto.class);
    }

    @Override
    public EmailMessage convertToEntity(EmailMessageDto emailDto) {
        return new ModelMapper().map(emailDto, EmailMessage.class);
    }

    private EmailMessage getEmailByIdOrThrowException(Long emailId) throws EntityNotFoundException {
        return emailRepository.findById(emailId).orElseThrow(
                () -> new EntityNotFoundException(EmailMessage.class, "id", emailId.toString())
        );
    }

    private UserDto findRecipientInDatabase(String recipientEmail) {
        return userService.findByEmail(recipientEmail);
    }

}