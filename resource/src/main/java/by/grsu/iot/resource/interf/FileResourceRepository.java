package by.grsu.iot.resource.interf;

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
     * @param file {@link MultipartFile} file
     * @return saved file name. File name not equals {@link MultipartFile#getOriginalFilename()}
     * @throws IOException exception when call method {@link MultipartFile#getInputStream()}
     */
    String save(MultipartFile file) throws IOException;
}
