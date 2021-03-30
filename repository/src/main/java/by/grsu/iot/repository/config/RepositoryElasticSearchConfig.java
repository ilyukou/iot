package by.grsu.iot.repository.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Configuration ElasticSearch Client
 *
 * @author Ilyukou Ilya
 */
@PropertySource("classpath:application-repository.properties")
@Configuration
public class RepositoryElasticSearchConfig {

    private static final String PORT = "by.grsu.iot.elasticsearch.port";
    private static final String HOST = "by.grsu.iot.elasticsearch.host";

    private final Environment env;

    public RepositoryElasticSearchConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(env.getProperty(HOST), 9200));
        return new RestHighLevelClient(builder);
    }

    @Bean
    public Client client() throws UnknownHostException {
        Settings elasticSearchSettings = Settings.builder()
//                .put("client.transport.sniff", true)
//                .put("Content-type","application/json")
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(elasticSearchSettings);
        client.addTransportAddress(new TransportAddress(InetAddress
                .getByName(
                        env.getProperty(HOST)),
                Integer.valueOf(env.getProperty(PORT))));

        return client;
    }
}
