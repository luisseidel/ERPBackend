package com.seidelsoft.ERPBackend.menu.service;

import com.seidelsoft.ERPBackend.authorization.entity.User;
import com.seidelsoft.ERPBackend.menu.model.Menu;
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

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService extends BaseService<Menu, MenuRepository> {

    /**
     * Busca todos os itens do menu ativos ordenados por posição
     * Resultado é cacheado para melhor performance
     * @return Lista de itens do menu
     */
    @Cacheable("menuItems")
    public List<Menu> findAllMenuItems() {
        return getSpecificRepository().findAllActiveOrderByPosition();
    }

    /**
     * Busca apenas os menus raiz (sem pai) ativos
     * Resultado é cacheado para melhor performance
     * @return Lista de menus raiz
     */
    @Cacheable("menuHierarchy")
    public List<Menu> findRootMenus() {
        return getSpecificRepository().findRootMenusActive();
    }

    /**
     * Busca menus raiz com seus filhos carregados
     * @return Lista de menus raiz com filhos
     */
    @Cacheable("menuHierarchy")
    public List<Menu> findRootMenusWithChildren() {
        return getSpecificRepository().findRootMenusWithChildren();
    }

    /**
     * Busca o menu marcado como página inicial
     * @return Menu da página inicial ou null
     */
    @Cacheable("menuItems")
    public Optional<Menu> findHomePageMenu() {
        return getSpecificRepository().findHomePageMenu();
    }

    /**
     * Busca todos os menus para paginação (usado no CRUD)
     * @param pageable Configuração de paginação
     * @return Página de menus
     */
    public Page<Menu> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Busca todos os menus ordenados (usado no CRUD)
     * @param sort Configuração de ordenação
     * @return Lista de menus ordenada
     */
    public List<Menu> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Cacheable("menuHierarchy")
    public List<Menu> findRootMenusWithChildrenByUser() {
        User usuarioLogado = getCurrentUser();
        List<Menu> rootMenus = getSpecificRepository().findAllRootMenusWithChildren();

        // Filtra recursivamente menus e filhos
        return rootMenus.stream()
                .map(menu -> filterMenuRecursively(usuarioLogado, menu))
                .filter(menu -> menu != null)
                .toList();
    }

    private Menu filterMenuRecursively(User usuario, Menu menu) {
        // Se o menu exige permissão e o usuário não tem, retorna null
        if (menu.getPermission() != null && !usuarioTemAcesso(usuario, menu)) {
            return null;
        }

        // Se tiver filhos, aplica o filtro recursivo
        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            List<Menu> filteredChildren = menu.getChildren().stream()
                    .map(child -> filterMenuRecursively(usuario, child))
                    .filter(child -> child != null)
                    .toList();
            menu.setChildren(filteredChildren); // substitui os filhos pelos filtrados
        }

        return menu;
    }

    private boolean usuarioTemAcesso(User usuario, Menu menu) {
        return usuario.getRoles().stream()
                .flatMap(role -> role.getRolePermissions().stream())
                .anyMatch(rp ->
                        rp.getPermission().equals(menu.getPermission()) &&
                                (rp.isConsultar() || rp.isAdicionar() || rp.isEditar() || rp.isExcluir())
                );
    }


    /**
     * Salva um item do menu e limpa o cache
     * @param menu Item do menu a ser salvo
     * @return Menu salvo
     */
    @Override
    @Transactional
    @CacheEvict(value = {"menuItems", "menuHierarchy"}, allEntries = true)
    public Menu save(Menu menu) {
        // Se este menu está sendo marcado como home page, desmarcar outros
        if (Boolean.TRUE.equals(menu.getHomePage())) {
            getSpecificRepository().findHomePageMenu().ifPresent(existingHome -> {
                if (!existingHome.getId().equals(menu.getId())) {
                    existingHome.setHomePage(false);
                    repository.save(existingHome);
                }
            });
        }

        return repository.save(menu);
    }

    /**
     * Limpa manualmente o cache de menus
     */
    @CacheEvict(value = {"menuItems", "menuHierarchy"}, allEntries = true)
    public void clearCache() {
        log.debug("Cache de menus limpo manualmente");
    }

    @Override
    public Optional<Menu> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"menuItems", "menuHierarchy"}, allEntries = true)
    public void delete(Long id) {
        repository.deleteById(id);
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
    }

}
