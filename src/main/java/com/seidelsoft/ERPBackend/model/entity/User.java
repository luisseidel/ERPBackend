package com.seidelsoft.ERPBackend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_generator")
    @SequenceGenerator(name = "usuario_generator", sequenceName = "seq_usuario", allocationSize = 1)
    private Long id;

	@Column(name = "email", length = 255, nullable = false)
    private String email;


	@Column(name = "name", length = 255, nullable = false)
    private String name;

	@Column(name = "password", length = 255, nullable = false)
	private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}, name = "unique_role_user"),
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id", table = "users", unique = false, foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "roles", unique = false, foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT))
    )
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
