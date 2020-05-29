package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "email_messages")
@Data
public class EmailMessage extends GenericEntityID {

    private String host;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String subject;
    private String message;

}
