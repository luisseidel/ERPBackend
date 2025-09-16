package com.seidelsoft.ERPBackend.authorization.repository;

import com.seidelsoft.ERPBackend.authorization.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
