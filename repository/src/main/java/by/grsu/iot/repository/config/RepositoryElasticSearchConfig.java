package by.grsu.iot.repository.config;

import org.elasticsearch.client.Client;
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

@PropertySource("classpath:application-repository.properties")
@Configuration
public class RepositoryElasticSearchConfig {

    private static final String PORT_9200 = "by.grsu.iot.elasticsearch.port";
    private static final String HOST = "by.grsu.iot.elasticsearch.host";
    private static final Integer PORT_9300 = 9300;

    private final Environment env;

    public RepositoryElasticSearchConfig(Environment env) {
        this.env = env;
    }

//    @Bean
//    public RestHighLevelClient restHighLevelClient() {
//        Integer port = Integer.parseInt(env.getProperty(PORT_9200));
//        RestClientBuilder builder = RestClient.builder(
//                new HttpHost(env.getProperty(HOST), port));
//        return new RestHighLevelClient(builder);
//    }

    // FIXME
    @Bean
    public Client client() throws UnknownHostException {
        Settings elasticSearchSettings = Settings.builder()
//                .put("client.transport.sniff", true)
                //  .put("Content-type","application/json")
                .put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(elasticSearchSettings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName(env.getProperty(HOST)), 9300));

        return client;
    }
}
