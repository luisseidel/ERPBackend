package com.seidelsoft.ERPBackend.authorization.repository;

import com.seidelsoft.ERPBackend.authorization.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
