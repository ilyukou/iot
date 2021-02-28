package by.grsu.iot.service.validation.impl;

import by.grsu.iot.service.domain.ValidationRule;
import by.grsu.iot.service.exception.BadRequestException;
import by.grsu.iot.service.util.StringUtil;
import by.grsu.iot.service.domain.ValidationStorage;
import by.grsu.iot.service.validation.interf.ProjectValidationService;
import org.springframework.stereotype.Service;

@Service
public class ProjectValidationServiceImpl implements ProjectValidationService {

    private final ValidationRule nameRule;
    private final ValidationRule titleRule;

    public ProjectValidationServiceImpl(ValidationStorage validationStorage) {
        this.nameRule = validationStorage.getProject().get("name");
        this.titleRule = validationStorage.getProject().get("title");
    }


    @Override
    public void validateName(String name) throws BadRequestException {
        if(name == null){
            throw new BadRequestException("name", "Name is null");
        }

        if(name.length() < nameRule.getMin()){
            throw new BadRequestException("name", "Name length is less than " + nameRule.getMin());
        }

        if(name.length() > nameRule.getMax()){
            throw new BadRequestException("name", "Name length is more than " + nameRule.getMax());
        }

        if(!StringUtil.isStringValidByParam(nameRule.getSpace(), name)){
            throw new BadRequestException("name", "Name string has space");
        }
    }

    @Override
    public void validateTitle(String title) throws BadRequestException {
        if(title == null){
            throw new BadRequestException("title", "Title is null");
        }

        if(title.length() < titleRule.getMin()){
            throw new BadRequestException("title", "Title length is less than " + titleRule.getMin());
        }

        if(title.length() > titleRule.getMax()){
            throw new BadRequestException("title", "Title length is more than " + titleRule.getMax());
        }

        if(!StringUtil.isStringValidByParam(titleRule.getSpace(), title)){
            throw new BadRequestException("title", "Title string has space");
        }
    }
}
