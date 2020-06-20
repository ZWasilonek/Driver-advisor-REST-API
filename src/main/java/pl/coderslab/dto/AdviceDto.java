package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class AdviceDto {

    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String guide;

    @JsonIgnore
    private LocalDate created;

    @JsonIgnore
    private LocalDate updated;

    private Integer recommendations;

    private Integer shares;

    private URL adviceFileURL;

    @NotNull
    private UserDto admin;

    private Set<TagDto> tags;

    private TrainingDto training;

    public AdviceDto() {
        this.tags = new HashSet<>();
    }

}