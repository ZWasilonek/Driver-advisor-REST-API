package pl.coderslab.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Getter
@Setter
public class AnswerDto {

    private Long id;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Boolean isCorrect;

    private URL answerFileURL;

}
