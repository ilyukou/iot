package by.grsu.iot.api.config.repository;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfig {

    @Value("${by.grsu.iot.resource.s3.endpoint}")
    private String S3_ENDPOINT_PROPERTY;

    @Value("${by.grsu.iot.resource.s3.accessKey}")
    private String S3_ACCESS_KEY_PROPERTY;

    @Value("${by.grsu.iot.resource.s3.secretKey}")
    private String S3_SECRET_KEY_PROPERTY;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(S3_ACCESS_KEY_PROPERTY, S3_SECRET_KEY_PROPERTY);

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(S3_ENDPOINT_PROPERTY, Regions.US_EAST_1.name()))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        return s3Client;
    }
}
