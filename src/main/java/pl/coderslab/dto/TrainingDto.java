package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TrainingDto {

    private Long id;

    private Integer maxScore;

    @NotNull
    @NotEmpty
    private Set<QuestionDto> questions;

    @JsonIgnore
    private LocalDate created;

    @JsonIgnore
    private LocalDate updated;

    public TrainingDto() {
        questions = new HashSet<>();
    }

}