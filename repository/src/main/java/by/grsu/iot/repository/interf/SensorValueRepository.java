package by.grsu.iot.repository.interf;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;

import java.io.IOException;
import java.util.List;

/**
 * Repository for CRUD operation with {@link SensorValueElasticsearch}
 *
 * @author Ilyukou Ilya
 */
public interface SensorValueRepository {

    /**
     * Add {@link SensorValueElasticsearch} to repository
     *
     * @param token {@link SensorValueElasticsearch#getToken()}
     * @param time  {@link SensorValueElasticsearch#getTime()}
     * @param value {@link SensorValueElasticsearch#getValue()}
     */
    SensorValueElasticsearch add(String token, Long time, Double value);

    /**
     * Get {@link List} of  {@link SensorValueElasticsearch}.
     *
     * @param token {@link SensorValueElasticsearch#getToken()}
     * @param from  equals or more than {@link SensorValueElasticsearch#getTime()}
     * @param to    equals or less than  {@link SensorValueElasticsearch#getTime()}
     * @param size  size of returned {@link List}
     * @return {@link List} of {@link SensorValueElasticsearch}
     */
    List<SensorValueElasticsearch> get(String token, Long from, Long to, Integer size) throws IOException;

    /**
     * Get the last {@link SensorValueElasticsearch}.
     *
     * @param token {@link SensorValueElasticsearch#getToken()}
     * @return {@link SensorValueElasticsearch} or {@code null}
     */
    SensorValueElasticsearch get(String token) throws IOException;

    /**
     * Get count of document with such {@link SensorValueElasticsearch#getToken()}
     *
     * @param token {@link SensorValueElasticsearch#getToken()}
     * @return count of document with such token.
     */
    Long getSensorValueElasticsearchCount(String token) throws IOException;

    List<SensorValueElasticsearch> getLastValuePiece(String token, Integer size);
}