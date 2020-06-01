package pl.coderslab.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TagDto {

    private Long id;

    @NotNull
    @NotBlank
    private String name;

}
