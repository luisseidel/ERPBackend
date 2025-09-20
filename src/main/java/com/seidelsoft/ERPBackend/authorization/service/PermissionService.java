package com.seidelsoft.ERPBackend.authorization.service;

import com.seidelsoft.ERPBackend.authorization.entity.Permission;
import com.seidelsoft.ERPBackend.authorization.repository.PermissionRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends BaseService<Permission, PermissionRepository> {
    @Override
    public boolean validar(Permission entity, StringBuilder msgValidacao) {
        return super.validar(entity, msgValidacao);
    }

}
