package by.grsu.iot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Used by the wrapper for the returned object in order to supplement the response with some text message and status
 *
 * @param <T> any object which need to send from controller
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HttpMessageWrapper<T> {

    private HttpMessageEnum status;
    private String message;
    private T body;
}
