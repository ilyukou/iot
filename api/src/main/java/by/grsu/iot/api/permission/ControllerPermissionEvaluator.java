package by.grsu.iot.api.permission;

import by.grsu.iot.api.permission.resolver.PermissionResolver;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;

@Service
public class ControllerPermissionEvaluator implements PermissionEvaluator {

    private static final String CREATE_LABEL = "create";
    private static final String READ_LABEL = "read";
    private static final String READ_PAGE_LABEL = "read-page";
    private static final String UPDATE_LABEL = "update";
    private static final String DELETE_LABEL = "delete";

    private final List<PermissionResolver> permissionResolvers;

    public ControllerPermissionEvaluator(List<PermissionResolver> permissionResolvers) {
        this.permissionResolvers = permissionResolvers;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new IllegalArgumentException("Not support such method check permission");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null || targetId == null || targetType == null || permission == null) {
            throw new IllegalArgumentException("One or more arguments are null");
        }

        List<PermissionResolver> resolverList = permissionResolvers
                .stream()
                .filter(resolver -> resolver.support(targetType))
                .collect(Collectors.toList());

        if (resolverList.size() == 0) {
            throw new IllegalArgumentException("Not supported such target type " + targetType);
        }

        for (PermissionResolver resolver : resolverList) {
            if (!resolve(((User) authentication.getPrincipal()).getUsername(), resolver, (String) permission, (Long) targetId)) {
                return false;
            }
        }

        return true;
    }

    private boolean resolve(String username, PermissionResolver resolver, String operation, Long id) {
        return Match(operation).of(
                Case($(CREATE_LABEL), () -> resolver.hasPermissionCreate(username, id)),
                Case($(READ_LABEL), () -> resolver.hasPermissionRead(username, id)),
                Case($(READ_PAGE_LABEL), () -> resolver.hasPermissionReadPage(username, id)),
                Case($(UPDATE_LABEL), () -> resolver.hasPermissionUpdate(username, id)),
                Case($(DELETE_LABEL), () -> resolver.hasPermissionDelete(username, id)),
                Case($(), false));
    }
}
