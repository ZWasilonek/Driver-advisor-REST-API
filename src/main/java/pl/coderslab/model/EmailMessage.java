package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "emails")
@Data
public class EmailMessage extends GenericEntityID {

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    private String subject;
    private String message;

}
