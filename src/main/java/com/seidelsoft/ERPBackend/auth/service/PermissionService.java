package com.seidelsoft.ERPBackend.auth.service;

import com.seidelsoft.ERPBackend.auth.model.entity.Permission;
import com.seidelsoft.ERPBackend.auth.repository.PermissionRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends BaseService<Permission, PermissionRepository> {
    @Override
    public boolean validar(Permission entity, StringBuilder msgValidacao) {
        return false;
    }

}
