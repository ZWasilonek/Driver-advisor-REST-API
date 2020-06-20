package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EmailMessageDto {

    private Long id;

    private UserDto sender;

    private UserDto recipient;

    private String subject;
    private String message;

    @JsonIgnore
    private LocalDateTime sentTime;

}