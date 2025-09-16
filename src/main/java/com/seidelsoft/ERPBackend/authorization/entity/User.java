package com.seidelsoft.ERPBackend.authorization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class User implements UserDetails {

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

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "account_expired", nullable = false)
    private Boolean accountExpired;

    @Column(name = "account_locked", nullable = false)
    private Boolean accountLocked;

    @Column(name = "credentials_expired", nullable = false)
    private Boolean credentialsExpired;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(role);
            authorities.addAll(role.getRolePermissions());
        }
        return authorities;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (accountExpired != null) {
            return Boolean.FALSE.compareTo(accountExpired) == 0;
        }
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (accountLocked != null) {
            return Boolean.FALSE.compareTo(accountLocked) == 0;
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (credentialsExpired != null) {
            return Boolean.FALSE.compareTo(credentialsExpired) == 0;
        }
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (enabled != null) {
            return Boolean.TRUE.compareTo(enabled) == 0;
        }
        return true;
    }
}
