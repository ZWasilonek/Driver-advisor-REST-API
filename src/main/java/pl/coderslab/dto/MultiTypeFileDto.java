package pl.coderslab.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MultiTypeFileDto {

    @Id
    @JsonIgnore
    private Long id;

    @NotNull
    @NotBlank
    private String fileName;

    @JsonIgnore
    private String fileType;

    @JsonIgnore
    private String uploadDir;

    @JsonIgnore
    private Long size;

    @JsonIgnore
    private byte[] data;

    public MultiTypeFileDto(String fileName, String fileType, Long size, String uploadDir) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.uploadDir = uploadDir;
    }

    public MultiTypeFileDto() {}

}
