package com.seidelsoft.ERPBackend.menu.repository;

import com.seidelsoft.ERPBackend.menu.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("""
        SELECT m
        FROM Menu m
        WHERE m.active = true
        ORDER BY m.orderPosition, m.name
        """)
    List<Menu> findAllActiveOrderByPosition();

    @Query("""
        SELECT m
        FROM Menu m
        WHERE m.homePage = true
          AND m.active = true
        """)
    Optional<Menu> findHomePageMenu();

    @Query("""
        SELECT DISTINCT m
        FROM Menu m
        LEFT JOIN FETCH m.children c
        WHERE m.parent IS NULL
          AND m.active = true
        ORDER BY m.orderPosition, m.name
        """)
    List<Menu> findRootMenusWithChildren();

    String findNameById(Long id);
}
