package by.grsu.iot.repository.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:application-repository.properties")
@Configuration
public class RepositoryElasticSearchConfig {

    private final Environment env;

    public RepositoryElasticSearchConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        Integer port = Integer.parseInt(env.getProperty("iot.elasticsearch.port"));
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(env.getProperty("iot.elasticsearch.host"), port));
        return new RestHighLevelClient(builder);
    }
}