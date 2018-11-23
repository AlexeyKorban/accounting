package local.ldwx.accounting.service.datajpa;

import local.ldwx.accounting.ProjectTestData;
import local.ldwx.accounting.model.User;
import local.ldwx.accounting.service.AbstractUserServiceTest;
import local.ldwx.accounting.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static local.ldwx.accounting.Profiles.DATAJPA;
import static local.ldwx.accounting.UserTestData.USER;
import static local.ldwx.accounting.UserTestData.USER_ID;
import static local.ldwx.accounting.UserTestData.assertMatch;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

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
