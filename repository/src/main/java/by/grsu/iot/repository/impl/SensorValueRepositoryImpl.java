package by.grsu.iot.repository.impl;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import by.grsu.iot.model.util.CollectionUtil;
import by.grsu.iot.repository.elasticsearch.SensorValueElasticsearchRepository;
import by.grsu.iot.repository.interf.SensorValueRepository;
import by.grsu.iot.repository.util.ElasticsearchUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Repository
public class SensorValueRepositoryImpl implements SensorValueRepository {

    private final String SENSOR_VALUE_DOCUMENT_NAME = "sensorvalue";

    private final Integer LIST_SIZE = 100;

    private final SensorValueElasticsearchRepository repository;
    private final Client client;
    private final ElasticsearchUtil elasticsearchUtil;

    public SensorValueRepositoryImpl(
            SensorValueElasticsearchRepository repository,
            Client client,
            ElasticsearchUtil elasticsearchUtil
    ) {
        this.repository = repository;
        this.client = client;
        this.elasticsearchUtil = elasticsearchUtil;
    }

    @Override
    public void add(String token, Long time, Double value) {
        repository.save(new SensorValueElasticsearch(time, value, token));
    }

    @Override
    public List<SensorValueElasticsearch> get(String token, Long from, Long to) {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchBoolPrefixQuery("token", token))
                .must(QueryBuilders.rangeQuery("time")
                        .gte(from)
                        .lte(to));

        SearchResponse response = client.prepareSearch(SENSOR_VALUE_DOCUMENT_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .setSize(getDocumentSize(token).intValue())
                .get();

        return CollectionUtil.samplingList(elasticsearchUtil.convertToList(response), LIST_SIZE.doubleValue())
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public SensorValueElasticsearch get(String token) {
        return elasticsearchUtil.convert(findOne(token));
    }

    @Override
    public Long getDocumentSize(String token) {
        SearchResponse response = findOne(token);

        return  response.getInternalResponse().hits().getTotalHits().value;
    }

    private SearchResponse findOne(String token){
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchBoolPrefixQuery("token", token));

        return  client.prepareSearch(SENSOR_VALUE_DOCUMENT_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .setSize(1)
                .get();
    }
}