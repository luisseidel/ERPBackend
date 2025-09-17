package com.seidelsoft.ERPBackend.authorization.entity;

import com.seidelsoft.ERPBackend.system.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@SequenceGenerator(name = "role_generator", sequenceName = "seq_role", allocationSize = 1)
public class Role extends BaseEntity implements GrantedAuthority {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    private Long id;

	@Column(name = "name", length = 255)
	private String name;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<RolePermission> rolePermissions = new HashSet<>();

	@Override
	public String getAuthority() {
		return this.name;
	}

    @Override
    public String toString() {
        return name + " - " + active;
    }
}
