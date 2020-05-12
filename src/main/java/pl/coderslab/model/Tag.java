package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "tags")
@Data
public class Tag extends GenericEntityID {

    private String name;

}
