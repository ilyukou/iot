package by.grsu.iot.repository.impl;

import by.grsu.iot.model.elastic.DeviceStateElasticsearch;
import by.grsu.iot.repository.RepositoryApplication;
import by.grsu.iot.repository.config.ElasticsearchTestConfig;
import by.grsu.iot.repository.interf.DeviceStateQueueRepository;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Import(ElasticsearchTestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceStateQueueRepositoryImplTest {

    @Autowired
    private DeviceStateQueueRepository deviceStateQueueRepository;

    @Autowired
    private RestHighLevelClient client;

    private final String tokenFirst = "tokenFirst";
    private final String tokenSecond = "tokenSecond";

    private final List<String> tokens = Arrays.asList(tokenFirst, tokenSecond);

    private final String index = DeviceStateElasticsearch.class.getAnnotation(Document.class).indexName();

    @BeforeAll
    public CreateIndexResponse setUp() throws IOException {
            CreateIndexRequest request = new CreateIndexRequest(index);
            request.settings(Settings.builder()
                    .loadFromSource(Strings.toString(jsonBuilder()
                            .startObject()
//                            .startObject("analysis")
//                            .startObject("analyzer")
//                            .startObject("case_insensitive_analyzer")
//                            .field("tokenizer", "keyword")
//                            .field("type", "custom")
//                            .field("filter", new String[]{"lowercase"})
                            .endObject()
                            .endObject()
                            .endObject()
                            .endObject()), XContentType.JSON)
            );

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
//                builder.startObject("properties");
//                {
//                    builder.startObject("seminar_nummer");
//                    {
//                        builder.field("analyzer", "case_insensitive_analyzer");
//                    }
//                    builder.endObject();
//                }
//                builder.endObject();
            }
            builder.endObject();

            request.mapping(builder);

            return client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Before
    public void cleanIndex(){
        tokens.forEach(t -> deviceStateQueueRepository.delete(t));
    }

    @Test
    public void injectedComponentsAreNotNull(){
        Assert.assertNotNull(deviceStateQueueRepository);
        Assert.assertNotNull(client);
    }

    @Test
    public void put(){
        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenFirst));

        DeviceStateElasticsearch deviceState = new DeviceStateElasticsearch(tokenFirst, "off", new Date().getTime());
        deviceStateQueueRepository.put(deviceState);

        DeviceStateElasticsearch actual = deviceStateQueueRepository.get(tokenFirst);
        Assert.assertEquals(deviceState.getToken(), actual.getToken());
        Assert.assertEquals(deviceState.getState(), actual.getState());
        Assert.assertEquals(deviceState.getTime(), actual.getTime());
        Assert.assertNotNull(deviceState.getId());
    }

    @Test
    public void get(){
        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenFirst));
        Assert.assertNull(deviceStateQueueRepository.get(tokenFirst));

        DeviceStateElasticsearch deviceState = new DeviceStateElasticsearch(tokenFirst, "off", new Date().getTime());
        deviceStateQueueRepository.put(deviceState);

        DeviceStateElasticsearch actual = deviceStateQueueRepository.get(tokenFirst);
        Assert.assertEquals(deviceState.getToken(), actual.getToken());
        Assert.assertEquals(deviceState.getState(), actual.getState());
        Assert.assertEquals(deviceState.getTime(), actual.getTime());
        Assert.assertNotNull(deviceState.getId());
    }

    @Test
    public void isExist(){
        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenFirst));
        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenSecond));

        DeviceStateElasticsearch firstDeviceState = new DeviceStateElasticsearch(tokenFirst, "off", new Date().getTime());
        deviceStateQueueRepository.put(firstDeviceState);

        DeviceStateElasticsearch secondDeviceState = new DeviceStateElasticsearch(tokenSecond, "off", new Date().getTime());
        deviceStateQueueRepository.put(secondDeviceState);

        Assert.assertTrue(deviceStateQueueRepository.isExist(tokenFirst));
        Assert.assertTrue(deviceStateQueueRepository.isExist(tokenSecond));

        deviceStateQueueRepository.delete(tokenFirst);
        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenFirst));
        Assert.assertTrue(deviceStateQueueRepository.isExist(tokenSecond));
    }

    @Test
    public void delete(){
        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenFirst));
        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenSecond));

        DeviceStateElasticsearch firstDeviceState = new DeviceStateElasticsearch(tokenFirst, "off", new Date().getTime());
        deviceStateQueueRepository.put(firstDeviceState);

        DeviceStateElasticsearch secondDeviceState = new DeviceStateElasticsearch(tokenSecond, "off", new Date().getTime());
        deviceStateQueueRepository.put(secondDeviceState);

        Assert.assertTrue(deviceStateQueueRepository.isExist(tokenFirst));
        Assert.assertTrue(deviceStateQueueRepository.isExist(tokenSecond));

        deviceStateQueueRepository.delete(tokenFirst);
        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenFirst));
        Assert.assertTrue(deviceStateQueueRepository.isExist(tokenSecond));
    }

    @Test
    public void getAndDelete(){
        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenFirst));

        DeviceStateElasticsearch deviceState = new DeviceStateElasticsearch(tokenFirst, "off", new Date().getTime());
        deviceStateQueueRepository.put(deviceState);

        Assert.assertTrue(deviceStateQueueRepository.isExist(tokenFirst));

        DeviceStateElasticsearch actual = deviceStateQueueRepository.getAndDelete(tokenFirst);
        Assert.assertEquals(deviceState.getToken(), actual.getToken());
        Assert.assertEquals(deviceState.getState(), actual.getState());
        Assert.assertEquals(deviceState.getTime(), actual.getTime());
        Assert.assertNotNull(deviceState.getId());

        Assert.assertFalse(deviceStateQueueRepository.isExist(tokenFirst));
    }
}
