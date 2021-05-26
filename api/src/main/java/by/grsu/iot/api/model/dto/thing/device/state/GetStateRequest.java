package by.grsu.iot.api.model.dto.thing.device.state;

import lombok.NoArgsConstructor;

/**
 * @author Ilyukou Ilya
 */
@NoArgsConstructor
public class GetStateRequest extends StateRequest {

    public GetStateRequest(String token) {
        super(token, null);
    }
}