package pl.coderslab.model.generic;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class GenericEntityID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

