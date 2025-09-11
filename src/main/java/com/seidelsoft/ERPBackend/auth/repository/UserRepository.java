package com.seidelsoft.ERPBackend.auth.repository;

import com.seidelsoft.ERPBackend.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("""
        SELECT u FROM User u
        LEFT JOIN FETCH u.roles r
        LEFT JOIN FETCH r.rolePermissions rp
        LEFT JOIN FETCH rp.permission
        WHERE u.email = :email
        """)
    Optional<User> findByEmailWithPermissions(@Param("email") String email);
}
