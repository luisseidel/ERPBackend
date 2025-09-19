package com.seidelsoft.ERPBackend.authorization.service;

import com.seidelsoft.ERPBackend.authorization.entity.Permission;
import com.seidelsoft.ERPBackend.authorization.repository.PermissionRepository;
import com.seidelsoft.ERPBackend.system.service.CachableService;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends CachableService<Permission, PermissionRepository> {
    @Override
    public boolean validar(Permission entity, StringBuilder msgValidacao) {
        return false;
    }

}
