package pl.coderslab.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "roles")
@Getter
@Setter
public class Role extends GenericEntityID {

    @Column(name = "role")
    private String name;

}