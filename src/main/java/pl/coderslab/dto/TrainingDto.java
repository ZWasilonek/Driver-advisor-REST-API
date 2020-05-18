package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.model.Question;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TrainingDto {

    @Id
    @JsonIgnore
    private Long id;

    private Integer score;

    @NotNull
    @NotEmpty
    private Set<QuestionDto> questions;

    private URL urlToFile;

    @JsonIgnore
    private LocalDate created;

    @JsonIgnore
    private LocalDate updated;

    public TrainingDto() {
        questions = new HashSet<>();
    }

}
