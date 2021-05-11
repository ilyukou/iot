package by.grsu.iot.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * The model uses, after successful user authorization, to pass the authorization token
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationUser implements Serializable {

    private String username;
    private String token;
    private Long tokenValidity;
}
