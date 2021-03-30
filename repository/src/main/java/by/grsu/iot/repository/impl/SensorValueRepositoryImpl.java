package by.grsu.iot.repository.impl;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import by.grsu.iot.model.util.CollectionUtil;
import by.grsu.iot.repository.elasticsearch.SensorValueElasticsearchRepository;
import by.grsu.iot.repository.interf.SensorValueRepository;
import by.grsu.iot.repository.util.ElasticsearchUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Repository
public class SensorValueRepositoryImpl implements SensorValueRepository {

    private final String SENSOR_VALUE_DOCUMENT_NAME;

    private final SensorValueElasticsearchRepository repository;
    private final RestHighLevelClient restHighLevelClient;

    public SensorValueRepositoryImpl(
            SensorValueElasticsearchRepository repository,
            RestHighLevelClient restHighLevelClient
    ) {
        this.repository = repository;
        this.restHighLevelClient = restHighLevelClient;

        SENSOR_VALUE_DOCUMENT_NAME = SensorValueElasticsearch.class.getAnnotation(Document.class).indexName();
    }

    @Override
    public SensorValueElasticsearch add(String token, Long time, Double value) {
        return repository.save(new SensorValueElasticsearch(time, value, token));
    }

    @Override
    public List<SensorValueElasticsearch> get(String token, Long from, Long to, Integer size) throws IOException {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchBoolPrefixQuery("token", token))
                .must(QueryBuilders.rangeQuery("time")
                        .gte(from)
                        .lte(to));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(getSensorValueElasticsearchCount(token).intValue());

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices(SENSOR_VALUE_DOCUMENT_NAME);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        return CollectionUtil.samplingList(ElasticsearchUtil.convertToList(response), size.doubleValue())
                .stream()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public SensorValueElasticsearch get(String token) throws IOException {
        return ElasticsearchUtil.convert(findOne(token));
    }

    @Override
    public Long getSensorValueElasticsearchCount(String token) throws IOException {
        SearchResponse response = findOne(token);

        return response.getInternalResponse().hits().getTotalHits().value;
    }

    private SearchResponse findOne(String token) throws IOException {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchBoolPrefixQuery("token", token));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(1);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequest.indices(SENSOR_VALUE_DOCUMENT_NAME);
        searchRequest.source(searchSourceBuilder);

        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

}