package com.seidelsoft.SpringDemo.repository;

import com.seidelsoft.SpringDemo.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
