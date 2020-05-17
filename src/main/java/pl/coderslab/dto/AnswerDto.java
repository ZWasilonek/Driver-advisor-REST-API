package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Getter
@Setter
public class AnswerDto {

    @Id
    @JsonIgnore
    private Long id;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Boolean isCorrect;

    private URL fileURL;

    private Long fileId;

}
