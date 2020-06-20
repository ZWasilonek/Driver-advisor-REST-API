package pl.coderslab.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "training")
@Getter
public class Training extends GenericEntityID {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updated;

    @NotNull
    @Column(name = "max_score")
    private Integer maxScore;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "training_questions", joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions;

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

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

}