package by.grsu.iot.model.dto.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ilyukou Ilya
 */
@Getter
@AllArgsConstructor
public enum RequestSortType {
    ascending("ascending"),
    descending("descending");

    private final String value;
}
