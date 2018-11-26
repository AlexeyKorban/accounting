package ru.ldwx.accounting.service.datajpa;


import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.ldwx.accounting.UserTestData;
import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.service.AbstractProjectServiceTest;
import ru.ldwx.accounting.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ldwx.accounting.Profiles.DATAJPA;
import static ru.ldwx.accounting.ProjectTestData.*;
import static ru.ldwx.accounting.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
class DataJpaProjectServiceTest extends AbstractProjectServiceTest {
    @Test
    void testGetWithUser() throws Exception {
        Project project = service.getWithUser(ADMIN_PROJECT_ID, ADMIN_ID);
        assertMatch(ADMIN_PROJECT1, project);
        UserTestData.assertMatch(UserTestData.ADMIN, project.getUser());
    }

    @Test
    void testGetWithUserNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(PROJECT1_ID, ADMIN_ID));
    }
}
