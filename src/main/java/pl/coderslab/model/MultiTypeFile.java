package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "images")
@Data
public class MultiTypeFile extends GenericEntityID {

    @NotNull
    @NotBlank
    private String fileName;

    @NotNull
    @NotBlank
    private String fileType;

    @Lob
    private byte[] data;

}
