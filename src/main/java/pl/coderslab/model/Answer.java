package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "answers")
@Data
public class Answer extends GenericEntityID {

    private String description;
    private Boolean isCorrect;

}
