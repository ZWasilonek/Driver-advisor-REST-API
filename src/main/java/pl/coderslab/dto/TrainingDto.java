package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TrainingDto {

    @Id
    @JsonIgnore
    private Long id;

    private Integer maxScore;

    @NotNull
    @NotEmpty
    private Set<QuestionDto> questions;

    private List<URL> filesURL;

    @JsonIgnore
    private LocalDate created;

    @JsonIgnore
    private LocalDate updated;

    public TrainingDto() {
        questions = new HashSet<>();
    }

}
