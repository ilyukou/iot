package by.grsu.iot.util.service;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Util for parsing a {@link SearchResponse} to {@link SensorValueElasticsearch}
 *
 * @author Ilyukou Ilya
 */
@Component
public class ElasticsearchUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtil.class);

    private static ObjectMapper OBJECT_MAPPER;

    public ElasticsearchUtil(ObjectMapper objectMapper) {
        OBJECT_MAPPER = objectMapper;
    }

    public static SensorValueElasticsearch convert(SearchResponse response) {
        List<SensorValueElasticsearch> list = convertToList(response);

        if (list.size() > 1) {
            throw new IndexOutOfBoundsException("Found more than one SensorValueElasticsearch in SearchResponse");
        }

        return list.size() == 1 ? list.get(0) : null;
    }

    public static List<SensorValueElasticsearch> convertToList(SearchResponse response) {
        SearchHit[] res = response.getHits().getHits();

        List<SensorValueElasticsearch> list = new LinkedList<>();

        for (SearchHit sh : res) {

            SensorValueElasticsearch object = null;
            try {
                object = OBJECT_MAPPER.readValue(sh.getSourceAsString(), SensorValueElasticsearch.class);
            } catch (JsonProcessingException e) {
                LOGGER.warn("Error while convert SearchResponse to SensorValueElasticsearch", e);
            }

            list.add(object);
        }

        return list;
    }
}
