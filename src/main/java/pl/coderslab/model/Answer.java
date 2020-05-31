package pl.coderslab.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "answers")
@Getter
@Setter
public class Answer extends GenericEntityID {

    @NotNull
    @NotBlank
    private String description;

    /**
     * 1-true
     * 0-false
     */
    @NotNull
    private Boolean isCorrect;

    @Column(name = "file_id")
    private Long fileId;

}
