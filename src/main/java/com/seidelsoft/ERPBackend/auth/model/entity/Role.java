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
@Table(name = "role")
public class Role implements GrantedAuthority {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    @SequenceGenerator(name = "role_generator", sequenceName = "seq_role", allocationSize = 1)
	private Long id;

	@Column(name = "name", length = 255)
	private String name;

    @Column(name = "active", nullable = false)
    private Boolean active;

	@Override
	public String getAuthority() {
		return this.name;
	}
}
