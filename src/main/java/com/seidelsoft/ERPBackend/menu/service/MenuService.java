package com.seidelsoft.ERPBackend.menu.service;

import com.seidelsoft.ERPBackend.authorization.entity.User;
import com.seidelsoft.ERPBackend.menu.model.Menu;
import com.seidelsoft.ERPBackend.menu.model.MenuDTO;
import com.seidelsoft.ERPBackend.menu.repository.MenuRepository;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService extends BaseService<Menu, MenuRepository> {

    @Cacheable(cacheNames = "menuHierarchy")
    public List<MenuDTO> findRootMenusWithChildren() {
        return getSpecificRepository().findRootMenusWithChildren()
                .stream()
                .map(MenuMapper::toDTO)
                .toList();
    }

    @Cacheable(cacheNames = "menuItems")
    public Optional<MenuDTO> findHomePageMenu() {
        return getSpecificRepository().findHomePageMenu().map(MenuMapper::toDTO);
    }

    @Override
    @Cacheable(cacheNames = "menuItems")
    public Collection<Menu> findAll(Sort sort) {
        return super.findAll(sort);
    }

    public Page<Menu> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<MenuDTO> findRootMenusWithChildrenByUser() {
        User usuarioLogado = getCurrentUser();
        List<MenuDTO> rootMenus = findRootMenusWithChildren();

        return rootMenus.stream()
                .map(menu -> filterMenuRecursively(usuarioLogado, menu))
                .filter(Objects::nonNull)
                .toList();
    }

    private MenuDTO filterMenuRecursively(User usuario, MenuDTO menu) {
        if (menu.getPermission() != null && !usuarioTemAcesso(usuario, menu)) {
            return null;
        }

        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            menu.setChildren(
                    menu.getChildren().stream()
                            .map(child -> filterMenuRecursively(usuario, child))
                            .filter(Objects::nonNull)
                            .toList()
            );
        }
        return menu;
    }

    private boolean usuarioTemAcesso(User usuario, MenuDTO menu) {
        return usuario.getRoles().stream()
                .flatMap(role -> role.getRolePermissions().stream())
                .anyMatch(rp ->
                        rp.getPermission().equals(menu.getPermission()) &&
                                (rp.isConsultar() || rp.isAdicionar() || rp.isEditar() || rp.isExcluir())
                );
    }

    @Override
    @Transactional
    @CacheEvict(value = {"menuItems", "menuHierarchy"}, allEntries = true)
    public Menu save(Menu menu) {
        return super.save(menu);
    }

    @CacheEvict(value = {"menuItems", "menuHierarchy"}, allEntries = true)
    public void clearCache() {
        log.debug("Cache de menus limpo manualmente");
    }


    @Override
    public boolean validar(Menu entity, StringBuilder msgValidacao) {
        if (entity.getName() == null || entity.getName().trim().isEmpty()) {
            return false;
        }
        if (entity.getUrl() == null || entity.getUrl().trim().isEmpty()) {
            return false;
        }
        return msgValidacao.isEmpty();
    }

    @Override
    public void beforeSave(Menu item) {
        if (item.getOrderPosition() == null) {
            item.setOrderPosition(0);
        }
        if (item.getActive() == null) {
            item.setActive(true);
        }
        if (item.getHomePage() == null) {
            item.setHomePage(false);
        }

        if (Boolean.TRUE.equals(item.getHomePage())) {
            getSpecificRepository().findHomePageMenu().ifPresent(existingHome -> {
                if (!existingHome.getId().equals(item.getId())) {
                    existingHome.setHomePage(false);
                    repository.save(existingHome);
                }
            });
        }
    }

}
