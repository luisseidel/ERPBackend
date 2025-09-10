package com.seidelsoft.ERPBackend.auth.service;

import com.seidelsoft.ERPBackend.auth.model.entity.Role;
import com.seidelsoft.ERPBackend.auth.repository.RoleRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, RoleRepository> {

    @Override
    public boolean validar(Role entity) {
        return false;
    }
}
