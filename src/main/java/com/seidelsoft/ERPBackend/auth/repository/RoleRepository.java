package com.seidelsoft.ERPBackend.auth.repository;

import com.seidelsoft.ERPBackend.auth.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
