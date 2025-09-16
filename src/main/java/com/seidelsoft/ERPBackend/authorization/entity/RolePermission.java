package com.seidelsoft.ERPBackend.authorization.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_permission")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RolePermission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_permission_generator")
    @SequenceGenerator(name = "role_permission_generator", sequenceName = "seq_role_permission", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "role_fk"))
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false, foreignKey = @ForeignKey(name = "permission_fk"))
    private Permission permission;

    @Override
    public String getAuthority() {
        return permission.getName();
    }

    @Column(nullable = false)
    private boolean consultar = false;

    @Column(nullable = false)
    private boolean adicionar = false;

    @Column(nullable = false)
    private boolean editar = false;

    @Column(nullable = false)
    private boolean excluir = false;

}
