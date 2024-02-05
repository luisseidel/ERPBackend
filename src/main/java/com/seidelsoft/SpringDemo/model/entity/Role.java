package com.seidelsoft.SpringDemo.model.entity;

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
@Table(name="roles")
public class Role implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_generator")
	@SequenceGenerator(name = "roles_generator", sequenceName = "seq_roles", allocationSize = 1)
	private Long id;

	@Column(name = "name", length = 255)
	private String name;

	@Override
	public String getAuthority() {
		return this.name;
	}
}
