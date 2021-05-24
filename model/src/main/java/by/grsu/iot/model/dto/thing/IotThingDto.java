package by.grsu.iot.model.dto.thing;

import by.grsu.iot.model.dto.DataTransferObject;
import by.grsu.iot.model.sql.IotThing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.vavr.API.*;

/**
 * @author Ilyukou Ilya
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IotThingDto implements DataTransferObject {

    private Long id;
    private String name;
    private String token;
    private Long activity;
    private Long update;
    private Long create;

    public IotThingDto(IotThing iotThing) {
        this.id = iotThing.getId();
        this.name = iotThing.getName();
        this.token = iotThing.getToken();

        Match(true).of(
                Case($(iotThing.getActive() != null), o -> run(() -> setActivity(iotThing.getActive().getTime()))),
                Case($(iotThing.getUpdated() != null), o -> run(() -> setUpdate(iotThing.getUpdated().getTime()))),
                Case($(iotThing.getCreated() != null), o -> run(() -> setCreate(iotThing.getCreated().getTime())))
        );
    }
}
