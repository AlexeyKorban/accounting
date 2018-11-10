package local.ldwx.accounting.service.datajpa;


import local.ldwx.accounting.UserTestData;
import local.ldwx.accounting.model.Project;
import local.ldwx.accounting.service.AbstractProjectServiceTest;
import local.ldwx.accounting.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static local.ldwx.accounting.Profiles.DATAJPA;
import static local.ldwx.accounting.ProjectTestData.*;
import static local.ldwx.accounting.UserTestData.ADMIN_ID;

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
