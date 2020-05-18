package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.model.Role;
import pl.coderslab.model.Training;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    @Id
    @JsonIgnore
    private Long id;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @JsonIgnore
    private int enabled;

    private Set<TrainingDto> training;

    @JsonIgnore
    private Set<Role> roles;

    public UserDto() {
        roles = new HashSet<>();
    }

}
