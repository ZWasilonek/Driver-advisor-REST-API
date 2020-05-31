package pl.coderslab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDto {

    private Long id;

    private UserDto sender;

    private UserDto recipient;

    private String subject;
    private String message;

}
