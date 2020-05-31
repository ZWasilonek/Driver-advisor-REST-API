package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Getter
@Setter
public class MultiTypeFileDto {

    private Long id;

    @NotNull
    @NotBlank
    private String fileName;

    @JsonIgnore
    private String fileType;

    private URL uploadDir;

    @JsonIgnore
    private Long size;

    @JsonIgnore
    private byte[] data;

    public MultiTypeFileDto() {}

}
