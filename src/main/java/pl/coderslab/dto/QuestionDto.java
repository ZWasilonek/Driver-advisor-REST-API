package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.model.Answer;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class QuestionDto {

    @Id
    @JsonIgnore
    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotEmpty
    private Set<AnswerDto> answers;

    public QuestionDto() {
        answers = new HashSet<>();
    }

}
