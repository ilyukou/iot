package by.grsu.iot.service.impl.crud;

import by.grsu.iot.access.interf.crud.ResourceAccessService;
import by.grsu.iot.model.exception.ApplicationException;
import by.grsu.iot.model.exception.BadRequestApplicationException;
import by.grsu.iot.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.model.sql.Resource;
import by.grsu.iot.repository.interf.ResourceRepository;
import by.grsu.iot.resource.interf.FileResourceRepository;
import by.grsu.iot.service.interf.crud.ResourceCrudService;
import by.grsu.iot.util.service.StringUtil;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Transactional
@Service
public class ResourceCrudServiceImpl implements ResourceCrudService {

    private final StringUtil stringUtil;

    private final ResourceRepository resourceRepository;
    private final FileResourceRepository fileResourceRepository;
    private final ResourceAccessService resourceAccessService;
    @Value("#{'${by.grsu.iot.resource.file.content-type}'.split(',')}")
    private List<String> CONTENT_TYPES;

    public ResourceCrudServiceImpl(
            ResourceRepository resourceRepository,
            FileResourceRepository fileResourceRepository,
            ResourceAccessService resourceAccessService,
            StringUtil stringUtil
    ) {
        this.resourceRepository = resourceRepository;
        this.fileResourceRepository = fileResourceRepository;
        this.resourceAccessService = resourceAccessService;
        this.stringUtil = stringUtil;
    }

    @Override
    public Resource create(MultipartFile file, Long projectId, String username) {
        resourceAccessService.checkCreateAccess(username, projectId);

        String fileName;
        try {
            fileName = fileResourceRepository.save(file);
        } catch (IOException e) {
            throw new ApplicationException("Error while upload file");
        }

        return resourceRepository.create(fileName, projectId);
    }

    @Override
    public Resource create(Long project, HttpServletRequest request) throws IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new BadRequestApplicationException("MultipartContent content doesn't exist");
        }

        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iter = upload.getItemIterator(request);

        if (!iter.hasNext()) throw new BadRequestApplicationException("Request does not contain a file");

        FileItemStream item = iter.next();

        if (item.isFormField()) throw new BadRequestApplicationException("Expected not form field");
        if (!support(item.getName()))
            throw new BadRequestApplicationException("Not support such content type. Support only " + CONTENT_TYPES.toString());

        InputStream inputStream = item.openStream();

        CompleteMultipartUploadResult result = fileResourceRepository.upload(item.getName(), inputStream);

        try {
            inputStream.close();
        } catch (IOException e) {
            // ignore
        }

        return resourceRepository.create(result.getKey(), project);
    }

    @Override
    public InputStream get(String filename, String username) {
        resourceAccessService.checkReadAccess(username, filename);

        if (!resourceRepository.isExist(filename)) {
            throw new EntityNotFoundApplicationException("Not found resource with such filename");
        }

        return fileResourceRepository.get(filename);
    }

    @Override
    public Boolean support(String fileName) {
        return CONTENT_TYPES.contains(stringUtil.getContentTypeFromFileName(fileName));
    }
}
