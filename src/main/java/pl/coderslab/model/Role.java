package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "roles")
@Data
public class Role extends GenericEntityID {

    @Column(name = "role")
    private String name;

}