package ru.ldwx.accounting.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.ldwx.accounting.UserTestData;
import ru.ldwx.accounting.model.User;
import ru.ldwx.accounting.repository.inmemory.InMemoryUserRepositoryImpl;
import ru.ldwx.accounting.util.exception.NotFoundException;
import ru.ldwx.accounting.web.user.AdminRestController;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ldwx.accounting.UserTestData.ADMIN;

@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepositoryImpl repository;

    @BeforeEach
    void setUp() throws Exception {
        repository.init();
    }

    @Test
    void testDelete() throws Exception {
        controller.delete(UserTestData.USER_ID);
        Collection<User> users = controller.getAll();
        assertEquals(1, users.size());
        assertEquals(ADMIN, users.iterator().next());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                controller.delete(10));
    }
}
