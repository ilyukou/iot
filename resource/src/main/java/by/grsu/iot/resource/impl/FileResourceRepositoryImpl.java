package by.grsu.iot.resource.impl;

import by.grsu.iot.resource.interf.FileResourceRepository;
import by.grsu.iot.util.service.StringUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@PropertySource("classpath:application-resource.properties")
@Service
public class FileResourceRepositoryImpl implements FileResourceRepository {

    private static final String MODULE = "by.grsu.iot.resource.";
    private static final String S3_FILE_NAME_LENGTH = MODULE + "s3.filename.length";
    private static final String S3_BUCKET_NAME = MODULE + "s3.bucketName";

    private static String bucketName;
    private static Long filenameLength;

    private final AmazonS3 amazonS3;
    private final StringUtil stringUtil;

    public FileResourceRepositoryImpl(Environment environment, AmazonS3 amazonS3, StringUtil stringUtil) {
        this.amazonS3 = amazonS3;
        bucketName = environment.getProperty(S3_BUCKET_NAME);
        filenameLength = Long.valueOf(Objects.requireNonNull(environment.getProperty(S3_FILE_NAME_LENGTH)));
        this.stringUtil = stringUtil;
    }

    @Override
    public InputStream get(String filename) {
        GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, filename);

        S3Object objectPortion = amazonS3.getObject(rangeObjectRequest);
        return objectPortion.getObjectContent();
    }

    @Override
    public String save(MultipartFile file) throws IOException {

        String fileName = stringUtil.generateFileName(filenameLength, file.getOriginalFilename());

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
