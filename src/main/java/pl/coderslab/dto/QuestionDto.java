package pl.coderslab.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class QuestionDto {

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
