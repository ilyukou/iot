package by.grsu.iot.repository.config;

import fr.pilato.spring.elasticsearch.ElasticsearchRestClientFactoryBean;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Properties;

@TestConfiguration
public class RepositoryElasticSearchTestConfig {

    @Bean
    @Primary
    public RestHighLevelClient esClient() throws Exception {
        ElasticsearchRestClientFactoryBean factory = new ElasticsearchRestClientFactoryBean();
        factory.setEsNodes(new String[]{"http://127.0.0.1:9200"});

        // Begin: If you are running with x-pack
        Properties props = new Properties();
        props.setProperty("xpack.security.user", "elastic:changeme");
        factory.setProperties(props);
        // End: If you are running with x-pack

        factory.afterPropertiesSet();

//        CreateIndexRequest indexRequest = new CreateIndexRequest("deviceState");
//        factory.getObject().indices().create(indexRequest, null);

//        For test purpose or for continuous integration, you could force
//        the factory to clean the previous indices when starting the client.
//                It will remove all your datas for every index which has been defined.
//        Just set forceMapping property to true.
        factory.setForceMapping(true);

//        For test purpose or for continuous integration, you could force the factory to
//        clean the previous template when starting the client. Just set forceTemplate property to true.
        factory.setForceTemplate(true);

        return factory.getObject();
    }
}
