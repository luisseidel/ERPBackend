package com.seidelsoft.ERPBackend.menu.service;

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
        dto.setPermission(menu.getPermission());

        // Evita loop infinito: só copia ID e nome do parent
        if (menu.getParent() != null) {
            dto.setParent(new MenuDTO(
                    menu.getParent().getId(),
                    menu.getParent().getName(),
                    null, null, null, null, null,
                    null, null, null
            ));
        }

        // Recursão para filhos
        if (menu.getChildren() != null) {
            dto.setChildren(
                    menu.getChildren().stream()
                            .map(MenuMapper::toDTO)
                            .toList()
            );
        }

        return dto;
    }

}
