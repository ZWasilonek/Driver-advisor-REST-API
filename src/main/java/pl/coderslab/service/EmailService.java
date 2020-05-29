package pl.coderslab.service;

import pl.coderslab.model.EmailMessage;

public interface EmailService {

    boolean sendEmail(final EmailMessage email, String recipientEmail);

}
