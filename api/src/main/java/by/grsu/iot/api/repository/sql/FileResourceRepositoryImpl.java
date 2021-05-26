package by.grsu.iot.api.repository.sql;

import by.grsu.iot.api.util.StringUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileResourceRepositoryImpl implements FileResourceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileResourceRepositoryImpl.class);

    @Value("${by.grsu.iot.resource.s3.bucketName}")
    private String S3_BUCKET_NAME;

    @Value("${by.grsu.iot.resource.file.filename.length}")
    private Long S3_FILE_NAME_LENGTH;

    @Value("${by.grsu.iot.resource.s3.partSize}")
    private Integer PART_SIZE;

    private final AmazonS3 amazonS3;
    private final StringUtil stringUtil;

    public FileResourceRepositoryImpl(AmazonS3 amazonS3, StringUtil stringUtil) {
        this.amazonS3 = amazonS3;
        this.stringUtil = stringUtil;
    }

    @Override
    public InputStream get(String filename) {
        GetObjectRequest rangeObjectRequest = new GetObjectRequest(S3_BUCKET_NAME, filename);

        S3Object objectPortion = amazonS3.getObject(rangeObjectRequest);
        return objectPortion.getObjectContent();
    }

    @Override
    public String save(MultipartFile file) throws IOException {

        String fileName = stringUtil.generateFileName(S3_FILE_NAME_LENGTH, file.getOriginalFilename());

        InputStream inputStream = file.getInputStream();

        amazonS3.putObject(S3_BUCKET_NAME, fileName, inputStream, null);

        try {
            inputStream.close();
        } catch (IOException e) {
            // ignore
        }

        return fileName;
    }

    @Override
    public CompleteMultipartUploadResult upload(String filename, InputStream inputStream) {
        filename = stringUtil.generateFileName(S3_FILE_NAME_LENGTH, filename);

        String uploadId = initiateMultipartUpload(filename).getUploadId();

        List<UploadPartResult> results = uploadFile(filename, uploadId, inputStream);

        return completeMultipartUpload(filename, uploadId, results);
    }

    @SneakyThrows
    private List<UploadPartResult> uploadFile(String filename, String uploadId, InputStream inputStream) {
        List<UploadPartResult> res = new ArrayList<>();

        int part = 0;

        LOGGER.trace("Start upload {} with uploadId {}", filename, uploadId);
        while (true) {
            byte[] buff = inputStream.readNBytes(PART_SIZE);

            if (buff.length == 0) break;

            res.add(uploadPart(filename, uploadId, part++, buff));
            LOGGER.trace("Uploaded part#{}; with content length {}; heap size {}", part, buff.length, Runtime.getRuntime().totalMemory());
        }
        LOGGER.trace("End upload {} with uploadId {}", filename, uploadId);

        return res;
    }

    private InitiateMultipartUploadResult initiateMultipartUpload(String fileName) {
        return amazonS3.initiateMultipartUpload(new InitiateMultipartUploadRequest(S3_BUCKET_NAME, fileName));
    }

    private CompleteMultipartUploadResult completeMultipartUpload(String fileName, String uploadId, List<UploadPartResult> results) {
        CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest()
                .withBucketName(S3_BUCKET_NAME)
                .withKey(fileName)
                .withUploadId(uploadId)
                .withPartETags(results);

        return amazonS3.completeMultipartUpload(completeRequest);
    }

    private UploadPartResult uploadPart(String key, String uploadId, int partNumber, byte[] bytes) {
        UploadPartRequest part = new UploadPartRequest()
                .withBucketName(S3_BUCKET_NAME)
                .withKey(key)
                .withUploadId(uploadId)
                .withPartNumber(partNumber)
                .withInputStream(new ByteArrayInputStream(bytes))
                .withPartSize(bytes.length);
        return amazonS3.uploadPart(part);
    }
}
