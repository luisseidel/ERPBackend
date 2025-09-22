package com.seidelsoft.ERPBackend.menu.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class MenuRepositoryTest {

    @Test
    void findNameById() {
    }
}