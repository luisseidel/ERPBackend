package com.seidelsoft.ERPBackend.menu.service;

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
        log.debug("Buscando todos os itens do menu no banco de dados");
        return getSpecificRepository().findAllActiveOrderByPosition();
    }

    /**
     * Busca apenas os menus raiz (sem pai) ativos
     * Resultado é cacheado para melhor performance
     * @return Lista de menus raiz
     */
    @Cacheable("menuHierarchy")
    public List<Menu> findRootMenus() {
        log.debug("Buscando menus raiz no banco de dados");
        return getSpecificRepository().findRootMenusActive();
    }

    /**
     * Busca menus raiz com seus filhos carregados
     * @return Lista de menus raiz com filhos
     */
    @Cacheable("menuHierarchy")
    public List<Menu> findRootMenusWithChildren() {
        log.debug("Buscando menus raiz com filhos no banco de dados");
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
    public boolean validar(Menu entity) {
        return entity.getName() != null && !entity.getName().trim().isEmpty() &&
                       entity.getUrl() != null && !entity.getUrl().trim().isEmpty();
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

    @Override
    public void afterSave(Menu savedItem) {

    }

    @Override
    public void beforeDelete(Optional<Menu> item) {

    }

    @Override
    public void afterDelete() {

    }
}
