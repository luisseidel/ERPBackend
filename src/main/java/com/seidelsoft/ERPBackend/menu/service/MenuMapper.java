package com.seidelsoft.ERPBackend.menu.service;

import com.seidelsoft.ERPBackend.authorization.entity.Permission;
import com.seidelsoft.ERPBackend.menu.model.Menu;
import com.seidelsoft.ERPBackend.menu.model.MenuDTO;

public class MenuMapper {

    public static MenuDTO toDTO(Menu menu) {
        if (menu == null) {
            return null;
        }

        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setUrl(menu.getUrl());
        dto.setIcon(menu.getIcon());
        dto.setActive(menu.getActive());
        dto.setHomePage(menu.getHomePage());
        dto.setOrderPosition(menu.getOrderPosition());

        if (menu.getPermission() != null) {
            Permission originalPermission = menu.getPermission();
            Permission dtoPermission = new Permission();
            dtoPermission.setId(originalPermission.getId());
            dtoPermission.setName(originalPermission.getName());
            dto.setPermission(dtoPermission);
        }

        if (menu.getParent() != null) {
            dto.setParent(new MenuDTO(
                    menu.getParent().getId(),
                    menu.getParent().getName(),
                    null, null, null, null, null,
                    null, null, null
            ));
        }

        if (menu.getChildren() != null) {
            dto.setChildren(
                    menu.getChildren().stream()
                            .map(MenuMapper::toDTO)
                            .toList()
            );
        }

        return dto;
    }

    public static Menu toMenu(MenuDTO dto) {
        if (dto == null) {
            return null;
        }

        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setName(dto.getName());
        menu.setUrl(dto.getUrl());
        menu.setIcon(dto.getIcon());
        menu.setActive(dto.getActive());
        menu.setHomePage(dto.getHomePage());
        menu.setOrderPosition(dto.getOrderPosition());

        if (dto.getPermission() != null) {
            Permission originalPermission = dto.getPermission();
            Permission dtoPermission = new Permission();
            dtoPermission.setId(originalPermission.getId());
            dtoPermission.setName(originalPermission.getName());
            menu.setPermission(dtoPermission);
        }

        if (dto.getParent() != null) {
            menu.setParent(new Menu(
                    dto.getParent().getId(),
                    dto.getParent().getName(),
                    null, null, null, null, null,
                    null, null, null, null
            ));
        }

        if (dto.getChildren() != null) {
            menu.setChildren(
                    dto.getChildren().stream()
                            .map(MenuMapper::toMenu)
                            .toList()
            );
        }

        return menu;
    }

}
