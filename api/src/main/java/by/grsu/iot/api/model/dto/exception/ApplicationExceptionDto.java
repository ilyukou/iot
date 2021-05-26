package by.grsu.iot.api.model.dto.exception;

import by.grsu.iot.api.model.dto.DataTransferObject;
import by.grsu.iot.api.model.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Exception response dto for {@link ApplicationException}
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationExceptionDto implements DataTransferObject {

    private Long time;
    private String message;

    public ApplicationExceptionDto(Date time, String message) {
        this.time = time.getTime();
        this.message = message;
    }
}
