package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.model.Tag;
import pl.coderslab.model.Training;
import pl.coderslab.model.User;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class AdviceDto {

    @Id
    @JsonIgnore
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

    private Integer recommendation;

    private Integer shared;

    @NotNull
    private User admin;

    private Set<Tag> tags;

    private URL fileURL;

    private Long fileId;

    private Training training;

    public AdviceDto() {
        this.tags = new HashSet<>();
    }

}
