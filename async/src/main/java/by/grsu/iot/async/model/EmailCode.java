package by.grsu.iot.async.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ilyukou Ilya
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCode implements Serializable {

    private String address;
    private Integer code;
    private EmailCodeType type;
}
