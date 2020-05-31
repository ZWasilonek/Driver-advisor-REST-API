package pl.coderslab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDto {

    private UserDto sender;

    private UserDto recipient;

    private String subject;
    private String message;

}
