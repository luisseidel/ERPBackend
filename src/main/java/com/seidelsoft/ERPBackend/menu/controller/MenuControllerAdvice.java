package com.seidelsoft.ERPBackend.menu.controller;

import com.seidelsoft.ERPBackend.menu.model.MenuDTO;
import com.seidelsoft.ERPBackend.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * ControllerAdvice que adiciona automaticamente os itens do menu 
 * a todas as páginas que usam o layout base.html
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class MenuControllerAdvice {

    private final MenuService menuService;

    /**
     * Adiciona os itens do menu raiz a todas as páginas automaticamente.
     * Este método é executado antes de cada renderização de página.
     * Os menus são carregados do cache para melhor performance.
     *
     * @return Lista de menus raiz com seus filhos
     */
    @ModelAttribute("menuItems")
    public List<MenuDTO> addMenuItems() {
        try {
            return menuService.findRootMenusWithChildrenByUser();
        } catch (Exception e) {
            log.error("Erro ao carregar itens do menu: {}", e.getMessage());
            return List.of(); // Retorna lista vazia em caso de erro
        }
    }

    /**
     * Adiciona o menu da página inicial a todas as páginas.
     *
     * @return Menu da página inicial ou null
     */
    @ModelAttribute("homePageMenu")
    public MenuDTO addHomePageMenu() {
        try {
            return menuService.findHomePageMenu().orElse(null);
        } catch (Exception e) {
            log.error("Erro ao carregar menu da página inicial: {}", e.getMessage());
            return null;
        }
    }
}
