package ru.ldwx.accounting.web.json;

import org.junit.jupiter.api.Test;
import ru.ldwx.accounting.UserTestData;
import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.model.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.ldwx.accounting.ProjectTestData.ADMIN_PROJECT1;
import static ru.ldwx.accounting.ProjectTestData.PROJECTS;
import static ru.ldwx.accounting.ProjectTestData.assertMatch;

public class JsonUtilTest {

    @Test
    void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(ADMIN_PROJECT1);
        System.out.println(json);
        Project project = JsonUtil.readValue(json, Project.class);
        assertMatch(project, ADMIN_PROJECT1);
    }

    @Test
    void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(PROJECTS);
        System.out.println(json);
        List<Project> projects = JsonUtil.readValues(json, Project.class);
        assertMatch(projects, PROJECTS);
    }

    @Test
    void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}
