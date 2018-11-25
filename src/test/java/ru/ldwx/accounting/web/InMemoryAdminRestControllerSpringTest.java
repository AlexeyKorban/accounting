package ru.ldwx.accounting.web;

import ru.ldwx.accounting.UserTestData;
import ru.ldwx.accounting.model.User;
import ru.ldwx.accounting.repository.inmemory.InMemoryUserRepositoryImpl;
import ru.ldwx.accounting.util.exception.NotFoundException;
import ru.ldwx.accounting.web.user.AdminRestController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static ru.ldwx.accounting.UserTestData.ADMIN;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {
    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        repository.init();
    }

    @Test
    public void delete() throws Exception {
        controller.delete(UserTestData.USER_ID);
        Collection<User> users = controller.getAll();
        Assert.assertEquals(1, users.size());
        Assert.assertEquals(ADMIN, users.iterator().next());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        controller.delete(10);
    }
}
