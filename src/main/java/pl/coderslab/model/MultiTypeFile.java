package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "files")
@Data
public class MultiTypeFile extends GenericEntityID {

    @NotNull
    @NotBlank
    @Column(name = "file_name")
    private String fileName;

    @NotNull
    @NotBlank
    @Column(name = "file_type")
    private String fileType;

    private Long size;

    @Lob
    private byte[] data;

}
