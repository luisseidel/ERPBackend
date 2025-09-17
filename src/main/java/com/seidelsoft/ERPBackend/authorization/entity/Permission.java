package com.seidelsoft.ERPBackend.authorization.entity;

import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
@SequenceGenerator(name = "permission_generator", sequenceName = "seq_permission", allocationSize = 1)
public class Permission extends BaseEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_generator")
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
