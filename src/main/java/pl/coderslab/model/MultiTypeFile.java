package pl.coderslab.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.coderslab.model.generic.GenericEntityID;

import javax.persistence.Entity;
import javax.persistence.Lob;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "images")
@Data
public class MultiTypeFile extends GenericEntityID {

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    public MultiTypeFile() {}

    public MultiTypeFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
