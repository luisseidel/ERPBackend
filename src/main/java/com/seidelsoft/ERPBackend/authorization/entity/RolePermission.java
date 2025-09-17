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
@Table(name = "role_permission")
@SequenceGenerator(name = "role_permission_generator", sequenceName = "seq_role_permission", allocationSize = 1)
public class RolePermission extends BaseEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_permission_generator")
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

    @Column(name = "consultar", nullable = false)
    private boolean consultar = false;

    @Column(name = "adicionar", nullable = false)
    private boolean adicionar = false;

    @Column(name = "editar", nullable = false)
    private boolean editar = false;

    @Column(name = "excluir", nullable = false)
    private boolean excluir = false;

}
