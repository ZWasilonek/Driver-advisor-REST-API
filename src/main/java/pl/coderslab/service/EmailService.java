package pl.coderslab.service;

import pl.coderslab.dto.EmailMessageDto;
import pl.coderslab.model.EmailMessage;

public interface EmailService {

    void saveEmail(EmailMessageDto emailDto);
    EmailMessageDto findById(Long emailId);
    boolean removeById(Long emailId);
    boolean sendEmail(final EmailMessageDto email, String recipientEmail, String senderEmail);
    EmailMessageDto convertToObjectDTO(EmailMessage email);
    EmailMessage convertToEntity(EmailMessageDto emailDto);

}
