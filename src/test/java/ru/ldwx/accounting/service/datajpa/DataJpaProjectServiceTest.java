package ru.ldwx.accounting.service.datajpa;


import ru.ldwx.accounting.UserTestData;
import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.service.AbstractProjectServiceTest;
import ru.ldwx.accounting.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static ru.ldwx.accounting.Profiles.DATAJPA;
import static ru.ldwx.accounting.ProjectTestData.*;
import static ru.ldwx.accounting.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
public class DataJpaProjectServiceTest extends AbstractProjectServiceTest {
    @Test
    public void testGetWithUser() throws Exception {
        Project project = service.getWithUser(ADMIN_PROJECT_ID, ADMIN_ID);
        assertMatch(ADMIN_PROJECT1, project);
        UserTestData.assertMatch(UserTestData.ADMIN, project.getUser());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithUserNotFound() throws Exception {
        service.get(PROJECT1_ID, ADMIN_ID);
    }
}
