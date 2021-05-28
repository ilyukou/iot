package by.grsu.iot.api.config.repository;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Configuration ElasticSearch Client
 *
 * @author Ilyukou Ilya
 */
@EnableElasticsearchRepositories(basePackages = {"by.grsu.iot.api"})
@Configuration
public class RepositoryElasticSearchConfig {

    private static final String MODULE = "by.grsu.iot.repository.";

    @Value("${by.grsu.iot.repository.elasticsearch.port}")
    private Integer PORT;

    @Value("${by.grsu.iot.repository.elasticsearch.host}")
    private String HOST;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(HOST, PORT)));
    }
}
