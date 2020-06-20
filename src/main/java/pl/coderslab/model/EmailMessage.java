package pl.coderslab.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "emails")
public class EmailMessage extends GenericEntityID {

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    private String subject;
    private String message;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime sentTime;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    @PrePersist
    public void setSentTime() {
        this.sentTime = LocalDateTime.now();
    }

}