package by.grsu.iot.service.impl.crud;

import by.grsu.iot.access.interf.crud.ResourceAccessService;
import by.grsu.iot.model.exception.ApplicationException;
import by.grsu.iot.model.exception.EntityNotFoundApplicationException;
import by.grsu.iot.model.sql.Resource;
import by.grsu.iot.repository.interf.ResourceRepository;
import by.grsu.iot.resource.interf.FileResourceRepository;
import by.grsu.iot.service.interf.crud.ResourceCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Transactional
@Service
public class ResourceCrudServiceImpl implements ResourceCrudService {

    private final ResourceRepository resourceRepository;
    private final FileResourceRepository fileResourceRepository;
    private final ResourceAccessService resourceAccessService;

    public ResourceCrudServiceImpl(
            ResourceRepository resourceRepository,
            FileResourceRepository fileResourceRepository,
            ResourceAccessService resourceAccessService
    ) {
        this.resourceRepository = resourceRepository;
        this.fileResourceRepository = fileResourceRepository;
        this.resourceAccessService = resourceAccessService;
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
    public InputStream get(String filename, String username) {
        resourceAccessService.checkReadAccess(username, filename);

        if (!resourceRepository.isExist(filename)){
            throw new EntityNotFoundApplicationException("Not found resource with such filename");
        }

        return fileResourceRepository.get(filename);
    }

    @Override
    public Boolean support(String type) {
        return type.equals("jpg") || type.equals("png") || type.equals("jpeg");
    }
}
