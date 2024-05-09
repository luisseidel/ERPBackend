package com.seidelsoft.ERPBackend.repository;

import com.seidelsoft.ERPBackend.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
