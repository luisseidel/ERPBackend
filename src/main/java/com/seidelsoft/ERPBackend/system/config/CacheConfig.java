package com.seidelsoft.ERPBackend.system.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de cache para a aplicação
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configura o gerenciador de cache usando ConcurrentMapCacheManager
     * Para produção, considere usar Redis ou Hazelcast
     */
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        
        // Define os nomes dos caches que serão utilizados
        cacheManager.setCacheNames(java.util.Arrays.asList("menuItems", "menuHierarchy"));
        
        // Permite criação dinâmica de novos caches
        cacheManager.setAllowNullValues(false);
        
        return cacheManager;
    }
}
