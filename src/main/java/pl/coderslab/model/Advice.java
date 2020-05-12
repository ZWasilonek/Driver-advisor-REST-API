package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Advice extends GenericEntityID {

    private String title;
    private String guide;
    private LocalDate created;
    private LocalDate updated;
    private Integer recommendation;
    private Integer shared;

    @ManyToOne
    private User admin;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags;

    @OneToOne
    private Image image;

    @OneToOne
    private Training training;

}
