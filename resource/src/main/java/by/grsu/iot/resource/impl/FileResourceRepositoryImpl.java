package by.grsu.iot.resource.impl;

import by.grsu.iot.resource.interf.FileResourceRepository;
import by.grsu.iot.util.service.StringUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@PropertySource("classpath:application-resource.properties")
@Service
public class FileResourceRepositoryImpl implements FileResourceRepository {

    @Value("${by.grsu.iot.resource.s3.filename.length}")
    private Integer filenameLength;

    @Value("${by.grsu.iot.resource.s3.bucketName}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public FileResourceRepositoryImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public InputStream get(String filename) {
        GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, filename);

        S3Object objectPortion = amazonS3.getObject(rangeObjectRequest);
        return objectPortion.getObjectContent();
    }

    @Override
    public String save(MultipartFile file) throws IOException {

        String fileName = StringUtil.generateFileName(filenameLength, file.getOriginalFilename());

        InputStream inputStream = file.getInputStream();

        amazonS3.putObject(bucketName, fileName, inputStream, null);

        try {
            inputStream.close();
        } catch (IOException e) {
            // ignore
        }

        return fileName;
    }
}
