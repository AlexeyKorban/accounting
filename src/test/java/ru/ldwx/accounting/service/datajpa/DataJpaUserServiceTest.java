package ru.ldwx.accounting.service.datajpa;

import ru.ldwx.accounting.ProjectTestData;
import ru.ldwx.accounting.model.User;
import ru.ldwx.accounting.service.AbstractJpaUserServiceTest;
import ru.ldwx.accounting.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static ru.ldwx.accounting.Profiles.DATAJPA;
import static ru.ldwx.accounting.UserTestData.USER;
import static ru.ldwx.accounting.UserTestData.USER_ID;
import static ru.ldwx.accounting.UserTestData.assertMatch;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractJpaUserServiceTest {

    public void testGetWithProjects() throws Exception {
        User user = service.getWithProjects(USER_ID);
        assertMatch(user, USER);
        ProjectTestData.assertMatch(user.getProjects(), ProjectTestData.PROJECTS);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithProjectsNotFound() throws Exception {
        service.getWithProjects(1);
    }
}
