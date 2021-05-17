package by.grsu.iot.repository.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Configuration ElasticSearch Client
 *
 * @author Ilyukou Ilya
 */
@EnableElasticsearchRepositories(basePackages = {"by.grsu.iot.repository"})
@Configuration
public class RepositoryElasticSearchConfig {

    private static final String MODULE = "by.grsu.iot.repository.";

    private static final String PORT = MODULE + "elasticsearch.port";
    private static final String HOST = MODULE + "elasticsearch.host";

    private final Environment env;

    public RepositoryElasticSearchConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(env.getProperty(HOST), Integer.valueOf(env.getProperty(PORT))));
        return new RestHighLevelClient(builder);
    }
}
