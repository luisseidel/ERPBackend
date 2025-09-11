package com.seidelsoft.ERPBackend.auth.repository;

import com.seidelsoft.ERPBackend.auth.model.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
