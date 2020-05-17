package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "training")
@Data
public class Training extends GenericEntityID {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updated;
    private Integer score;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "training_questions", joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions;

    @Column(name = "file_id")
    private Long fileId;

    public Training() {
        questions = new HashSet<>();
    }

    @PrePersist
    public void setCreated() {
        this.created = LocalDate.now();
    }

    @PreUpdate
    public void setUpdated() {
        this.updated = LocalDate.now();
    }

}
