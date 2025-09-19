package com.seidelsoft.ERPBackend.authorization.service;

import com.seidelsoft.ERPBackend.authorization.entity.Permission;
import com.seidelsoft.ERPBackend.authorization.entity.Role;
import com.seidelsoft.ERPBackend.authorization.entity.RolePermission;
import com.seidelsoft.ERPBackend.authorization.repository.RolePermissionRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RolePermissionService extends BaseService<RolePermission, RolePermissionRepository> {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean validar(RolePermission entity, StringBuilder msgValidacao) {
        if (entity.getPermission() == null) {
            return false;
        }
        if (entity.getRole() == null) {
            return false;
        }
        return true;
    }

    public Collection<Role> findAllRoles() {
        return roleService.findAll(Sort.by("name"));
    }

    public Collection<Permission> findAllPermission() {
        return permissionService.findAll(Sort.by("name"));
    }

}
