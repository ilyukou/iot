package by.grsu.iot.resource.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@PropertySource("classpath:application-resource.properties")
@Configuration
public class ResourceConfig {

    private static final String MODULE = "by.grsu.iot.resource.";

    private static final String S3_ENDPOINT_PROPERTY = MODULE + "s3.endpoint";
    private static final String S3_ACCESS_KEY_PROPERTY = MODULE + "s3.accessKey";
    private static final String S3_SECRET_KEY_PROPERTY = MODULE + "s3.secretKey";

    private final Environment environment;

    public ResourceConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(
                Objects.requireNonNull(environment.getProperty(S3_ACCESS_KEY_PROPERTY)),
                Objects.requireNonNull(environment.getProperty(S3_SECRET_KEY_PROPERTY)));

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        environment.getProperty(S3_ENDPOINT_PROPERTY), Regions.US_EAST_1.name()))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        return s3Client;
    }
}
