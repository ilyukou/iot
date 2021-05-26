package by.grsu.iot.api.repository.sql;

import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.UploadPartRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ilyukou Ilya
 */
public interface FileResourceRepository {

    /**
     * Get {@link java.io.File} as {@link InputStream} by filename
     * @param filename required filename
     * @return {@link InputStream}
     */
    InputStream get(String filename);

    /**
     * Save {@link MultipartFile}
     *
     * @param file {@link MultipartFile} file
     * @return saved file name. File name not equals {@link MultipartFile#getOriginalFilename()}
     * @throws IOException exception when call method {@link MultipartFile#getInputStream()}
     */
    String save(MultipartFile file) throws IOException;

    /**
     * Upload {@link InputStream} using {@link UploadPartRequest}
     *
     * @param filename    filename
     * @param inputStream file content
     * @return {@link CompleteMultipartUploadResult}
     */
    CompleteMultipartUploadResult upload(String filename, InputStream inputStream);
}
