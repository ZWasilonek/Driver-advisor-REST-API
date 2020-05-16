package pl.coderslab.exception.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationError extends ApiSubError {

    private String object;
    private String field;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

}
