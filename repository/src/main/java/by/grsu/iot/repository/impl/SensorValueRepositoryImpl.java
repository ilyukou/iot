package by.grsu.iot.repository.impl;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import by.grsu.iot.repository.elasticsearch.SensorValueElasticsearchRepository;
import by.grsu.iot.repository.interf.SensorValueRepository;
import by.grsu.iot.repository.util.ElasticsearchUtil;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        MultiSearchRequest request = new MultiSearchRequest();

        double step = ((double) (to - from)) / size;

        for (int i = 0; i < size; i++) {
            Double f = from + i * step;
            Double t = from + (i + 1) * step;
            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchBoolPrefixQuery("token", token))
                    .must(QueryBuilders.rangeQuery("time")
                            .gte(f)
                            .lt(t));

            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.size(1);
            builder.query(queryBuilder);

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
            searchRequest.indices(SENSOR_VALUE_DOCUMENT_NAME);
            searchRequest.source(builder);

            request.add(searchRequest);
        }

        MultiSearchResponse response = restHighLevelClient.multiSearch(request, RequestOptions.DEFAULT);

        List<SensorValueElasticsearch> values = new ArrayList<>();

        for (MultiSearchResponse.Item item : response.getResponses()){
            if (item.getResponse().getHits().getTotalHits().value > 0){
                values.addAll(ElasticsearchUtil.convertToList(item.getResponse()));
            }
        }
        return values;
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