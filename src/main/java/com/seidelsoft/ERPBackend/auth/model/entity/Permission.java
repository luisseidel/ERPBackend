package com.seidelsoft.ERPBackend.auth.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_generator")
    @SequenceGenerator(name = "permission_generator", sequenceName = "seq_permission", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Override
    public String getAuthority() {
        return name;
    }
}
