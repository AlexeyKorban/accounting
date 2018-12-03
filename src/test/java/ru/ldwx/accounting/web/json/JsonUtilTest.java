package ru.ldwx.accounting.web.json;

import org.junit.jupiter.api.Test;
import ru.ldwx.accounting.model.Project;

import java.util.List;

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
}
