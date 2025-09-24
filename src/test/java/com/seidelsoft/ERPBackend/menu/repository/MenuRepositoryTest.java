package com.seidelsoft.ERPBackend.menu.repository;

import com.seidelsoft.ERPBackend.authorization.entity.Permission;
import com.seidelsoft.ERPBackend.menu.model.Menu;
import com.seidelsoft.ERPBackend.menu.model.MenuDTO;
import com.seidelsoft.ERPBackend.menu.service.MenuMapper;
import com.seidelsoft.ERPBackend.mock.MockUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class MenuRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    MenuRepository menuRepository;

    @BeforeEach
    void setup() {
        try (final AutoCloseable ac = MockitoAnnotations.openMocks(this)) {
            Permission permission = MockUtils.mockPermission();
            this.entityManager.persist(permission);
            this.entityManager.flush();
            Menu homePage = convertAndSaveMenu(MockUtils.mockMenu("Home Page Menu", Boolean.TRUE, Boolean.TRUE, null, null, permission));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Should get HomePage successfully from DB")
    void homePageExistsSuccess() {
        Optional<Menu> menu = menuRepository.findHomePageMenu();
        assertTrue(menu.isPresent() && Boolean.TRUE.equals(menu.get().getHomePage()));
    }

    @Test
    @DisplayName("Should get HomePage successfully from DB")
    void homePageExistsFail() {
        Optional<Menu> menu = menuRepository.findHomePageMenu();
        assertFalse(menu.isPresent() && Boolean.FALSE.equals(menu.get().getHomePage()));
    }

    private Menu convertAndSaveMenu(MenuDTO... menusDTOs) {
        for (MenuDTO menuDTO : menusDTOs) {
            Menu newMenu = MenuMapper.toMenu(menuDTO);
            this.entityManager.persist(newMenu);
            this.entityManager.flush();
            return newMenu;
        }
        return null;
    }

}