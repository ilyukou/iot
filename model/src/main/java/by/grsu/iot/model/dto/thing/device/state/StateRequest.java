package by.grsu.iot.model.dto.thing.device.state;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * State Request superclass for basic logic with state
 *
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class StateRequest {

    private String token;

    private String state;


    @Override
    public String toString() {
        return "StateRequest{" +
                "token='" + token + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
