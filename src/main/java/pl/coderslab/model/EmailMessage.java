package pl.coderslab.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "emails")
@Getter
@Setter
public class EmailMessage extends GenericEntityID {

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    private String subject;
    private String message;

}
