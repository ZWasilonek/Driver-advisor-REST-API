package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "trainings")
@Data
public class Training extends GenericEntityID {

    private LocalDate created;
    private LocalDate updated;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "training_questions", joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions;

}
