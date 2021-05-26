package by.grsu.iot.api.config.repository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.TimeZone;

/**
 * Configuration the database
 *
 * @author Ilyukou Ilya
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"by.grsu.iot.api"})
public class RepositoryDataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    private final Environment env;

    public RepositoryDataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        LoggerFactory.getLogger(RepositoryDataSourceConfig.class)
                .info("Spring boot application running in UTC timezone :" + new Date());
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan(new String[] { "by.grsu" });
//
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
//        properties.setProperty("spring.jpa.hibernate.ddl-auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
//        em.setJpaProperties(properties);
//
//        return em;
//    }
}
