package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.model.EmailMessage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private Long id;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @JsonIgnore
    private int enabled;

    private Integer score;

    private Set<TrainingDto> training;

    @JsonIgnore
    private Set<RoleDto> roles;

    private Set<EmailMessage> emails;

    public UserDto() {
        roles = new HashSet<>();
    }

}
