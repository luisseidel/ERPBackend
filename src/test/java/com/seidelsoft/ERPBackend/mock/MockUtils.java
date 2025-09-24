package com.seidelsoft.ERPBackend.mock;

import com.seidelsoft.ERPBackend.authorization.entity.Permission;
import com.seidelsoft.ERPBackend.authorization.entity.Role;
import com.seidelsoft.ERPBackend.authorization.entity.RolePermission;
import com.seidelsoft.ERPBackend.authorization.entity.User;
import com.seidelsoft.ERPBackend.menu.model.Menu;
import com.seidelsoft.ERPBackend.menu.model.MenuDTO;
import com.seidelsoft.ERPBackend.menu.service.MenuMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

public class MockUtils {

    public static User mockUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .name("teste")
                .email("asd@asd.com")
                .password(passwordEncoder.encode("teste"))
                .enabled(true)
                .accountLocked(false)
                .credentialsExpired(false)
                .accountExpired(false)
                .roles(Set.of(mockRole()))
                .build();
    }

    public static Role mockRole() {
        return Role.builder()
                .name("Admin")
                .active(true)
                .rolePermissions(Set.of(mockRolePermission()))
                .build();
    }

    public static RolePermission mockRolePermission() {
        return RolePermission.builder()
                .role(Role.builder().name("Admin").active(true).build())
                .permission(mockPermission())
                .adicionar(true)
                .editar(true)
                .excluir(true)
                .consultar(true)
                .build();
    }

    public static Permission mockPermission() {
        return Permission.builder()
                .name("Menu")
                .description("ASD")
                .build();
    }

    public static MenuDTO mockMenu(
            String name,
            Boolean active,
            Boolean isHomePage,
            Menu parent,
            Menu child,
            Permission permission
    ) {
        return new MenuDTO(
            null,
            name,
            "/pages/menu",
            "fafa 2",
            active,
            isHomePage,
            1,
            permission,
            MenuMapper.toDTO(parent),
            Collections.singletonList(MenuMapper.toDTO(child))
        );
    }

}
