package by.grsu.iot.repository.config;

import fr.pilato.spring.elasticsearch.ElasticsearchRestClientFactoryBean;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@TestConfiguration
public class RepositoryTestConfig {

    @Bean
    @Primary
    public RestHighLevelClient restHighLevelClient() throws Exception {
        ElasticsearchRestClientFactoryBean factory = new ElasticsearchRestClientFactoryBean();
        factory.setEsNodes(new String[]{"http://127.0.0.1:9200"});
        Properties props = new Properties();
        props.setProperty("xpack.security.user", "elastic:changeme");
        factory.setProperties(props);
        factory.afterPropertiesSet();
        factory.setForceMapping(true);
        factory.setForceTemplate(true);
        return factory.getObject();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        return dataSource;
    }
}
