package by.grsu.iot.resource.config;

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
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application-resource.properties")
@Configuration
public class ResourceConfig {

    @Value("${by.grsu.iot.resource.s3.endpoint}")
    private String endpoint;

    @Value("${by.grsu.iot.resource.s3.accessKey}")
    private String accessKey;

    @Value("${by.grsu.iot.resource.s3.secretKey}")
    private String secretKey;

    @Value("${by.grsu.iot.resource.s3.bucketName}")
    private String bucketName;

    @Bean
    public AmazonS3 amazonS3() {
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//        ClientConfiguration clientConfiguration = new ClientConfiguration();
//        clientConfiguration.setSignerOverride("AWSS3V4SignerType");
//
//        AmazonS3 amazonS3 =  AmazonS3ClientBuilder
//                .standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, Regions.US_EAST_1.name()))
//                .withPathStyleAccessEnabled(true)
//                .withClientConfiguration(clientConfiguration)
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//
//                .build();
//        amazonS3.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
//
//        return amazonS3;
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, Regions.US_EAST_1.name()))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        return s3Client;
    }
}
