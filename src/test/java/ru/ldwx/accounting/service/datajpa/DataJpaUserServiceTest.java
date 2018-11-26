package ru.ldwx.accounting.service.datajpa;


import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.ldwx.accounting.ProjectTestData;
import ru.ldwx.accounting.model.User;
import ru.ldwx.accounting.service.AbstractJpaUserServiceTest;
import ru.ldwx.accounting.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ldwx.accounting.Profiles.DATAJPA;
import static ru.ldwx.accounting.UserTestData.*;

@ActiveProfiles(DATAJPA)
class DataJpaUserServiceTest extends AbstractJpaUserServiceTest {

    @Test
    void testGetWithProjects() throws Exception {
        User user = service.getWithProjects(USER_ID);
        assertMatch(user, USER);
        ProjectTestData.assertMatch(user.getProjects(), ProjectTestData.PROJECTS);
    }

    @Test
    void testGetWithProjectsNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.getWithProjects(1));
    }
}
