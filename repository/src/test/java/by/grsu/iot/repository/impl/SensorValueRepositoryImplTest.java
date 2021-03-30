package by.grsu.iot.repository.impl;

import by.grsu.iot.model.elasticsearch.SensorValueElasticsearch;
import by.grsu.iot.repository.RepositoryApplication;
import by.grsu.iot.repository.config.RepositoryElasticSearchTestConfig;
import by.grsu.iot.repository.elasticsearch.SensorValueElasticsearchRepository;
import by.grsu.iot.repository.interf.SensorValueRepository;
import by.grsu.iot.repository.util.TimeUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Import(RepositoryElasticSearchTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SensorValueRepositoryImplTest {

    private final String INDEX_NAME = SensorValueElasticsearch.class.getAnnotation(Document.class).indexName();
    @Autowired
    private SensorValueRepository sensorValueRepository;
    @Autowired
    private SensorValueElasticsearchRepository sensorValueElasticsearchRepository;
    @Autowired
    private RestHighLevelClient client;

    private String token = "token";
    private String token2 = "token2";

    @BeforeAll
    public void createIndexResponse() throws IOException {

        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
        request.settings(Settings.builder()
//                .put("index.number_of_shards", indexDto.getNumberOfShards())
//                .put("index.number_of_replicas", indexDto.getNumberOfReplicas())
        );

        CreateIndexResponse response = client.indices()
                .create(request, RequestOptions.DEFAULT);
    }

    @BeforeEach
    @Before
    public void deleteDocument() {
        sensorValueElasticsearchRepository.deleteAll();
    }

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(sensorValueRepository);
    }

    @Test
    public void add() throws IOException {
        Double value = Math.random();
        long time = TimeUtil.getCurrentDate().getTime();

        Assert.assertEquals((Long) 0L, sensorValueRepository.getSensorValueElasticsearchCount(token));

        SensorValueElasticsearch valueElasticsearch = sensorValueRepository.add(token, time, value);

        Assert.assertEquals((Long) 1L, sensorValueRepository.getSensorValueElasticsearchCount(token));
        Assert.assertEquals(valueElasticsearch, sensorValueRepository.get(token));
    }

    @Test
    public void get() throws IOException {
        Double value = Math.random();
        long time = TimeUtil.getCurrentDate().getTime();

        Assert.assertNull(sensorValueRepository.get(token));
        Assert.assertNull(sensorValueRepository.get(token2));

        SensorValueElasticsearch valueElasticsearch = sensorValueRepository.add(token, time, value);

        Assert.assertEquals(valueElasticsearch, sensorValueRepository.get(token));
        Assert.assertNull(sensorValueRepository.get(token2));
    }

    @Test
    public void getSensorValueElasticsearchCount() throws IOException {
        Double value = Math.random();
        long time = TimeUtil.getCurrentDate().getTime();

        Assert.assertEquals((Long) 0L, sensorValueRepository.getSensorValueElasticsearchCount(token));

        sensorValueRepository.add(token, time, value);
        Assert.assertEquals((Long) 1L, sensorValueRepository.getSensorValueElasticsearchCount(token));

        sensorValueRepository.add(token, time, value);
        Assert.assertEquals((Long) 2L, sensorValueRepository.getSensorValueElasticsearchCount(token));
    }

    @Test
    public void getByParam() throws IOException, InterruptedException {
        Double v1 = Math.random();
        Double v2 = Math.random();
        Double v3 = Math.random();
        long t1 = TimeUtil.getCurrentDate().getTime();
        Thread.sleep(10);
        long t2 = TimeUtil.getCurrentDate().getTime();
        Thread.sleep(10);
        long t3 = TimeUtil.getCurrentDate().getTime();

        SensorValueElasticsearch e1 = sensorValueRepository.add(token, t1, v1);
        SensorValueElasticsearch e2 = sensorValueRepository.add(token, t2, v2);
        SensorValueElasticsearch e3 = sensorValueRepository.add(token, t3, v3);

        List<SensorValueElasticsearch> result = new ArrayList<>();

        result = sensorValueRepository.get(token, t1, t3, 3);
        Assert.assertEquals(3L, result.size());
        Assert.assertEquals(e1, result.get(0));
        Assert.assertEquals(e2, result.get(1));
        Assert.assertEquals(e3, result.get(2));

        result = sensorValueRepository.get(token, t1, t3 - 1, 3);
        Assert.assertEquals(2L, result.size());
        Assert.assertEquals(e1, result.get(0));
        Assert.assertEquals(e2, result.get(1));

        result = sensorValueRepository.get(token, t1, t2, 3);
        Assert.assertEquals(2L, result.size());
        Assert.assertEquals(e1, result.get(0));
        Assert.assertEquals(e2, result.get(1));

        result = sensorValueRepository.get(token, t1, t2 - 1, 3);
        Assert.assertEquals(1L, result.size());
        Assert.assertEquals(e1, result.get(0));

        result = sensorValueRepository.get(token, t1 - 2, t1 - 1, 3);
        Assert.assertEquals(0L, result.size());
    }
}
