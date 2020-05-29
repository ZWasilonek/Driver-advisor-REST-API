package pl.coderslab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDto {

    private String host;
    private User sender;
    private User receiver;
    private String subject;
    private String message;

}
