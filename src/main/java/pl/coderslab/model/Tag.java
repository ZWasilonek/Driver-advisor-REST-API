package pl.coderslab.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "tags")
@Getter
@Setter
public class Tag extends GenericEntityID {

    @NotBlank
    @NotNull
    private String name;

}
