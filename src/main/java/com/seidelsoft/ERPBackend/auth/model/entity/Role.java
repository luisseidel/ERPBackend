package com.seidelsoft.ERPBackend.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements GrantedAuthority {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    @SequenceGenerator(name = "role_generator", sequenceName = "seq_role", allocationSize = 1)
    @EqualsAndHashCode.Include
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
}
