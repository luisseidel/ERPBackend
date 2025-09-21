package com.seidelsoft.ERPBackend.menu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuCacheWarmer implements ApplicationRunner {

    private final MenuService menuService;
    private final CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Iniciando o aquecimento do cache de menus via ApplicationRunner...");
        try {
            // Limpa o cache antigo para garantir que não haja dados de uma execução anterior
            log.info("Limpando caches de menu existentes...");
            Objects.requireNonNull(cacheManager.getCache("menuHierarchy")).clear();
            Objects.requireNonNull(cacheManager.getCache("homePageMenu")).clear();

            // Chama o método que busca a hierarquia completa e a coloca no cache "menuHierarchy"
            log.info("Populando o cache 'menuHierarchy'...");
            menuService.findRootMenusWithChildren();

            log.info("Cache de menus aquecido com sucesso.");
        } catch (Exception e) {
            log.error("Falha ao aquecer o cache de menus durante a inicialização.", e);
        }
    }
}
