package ru.ldwx.accounting.web;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import ru.ldwx.accounting.model.User;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.ldwx.accounting.ProjectTestData.PROJECTS;
import static ru.ldwx.accounting.UserTestData.*;
import static ru.ldwx.accounting.util.ProjectsUtil.getWithExcess;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        new AssertionMatcher<List<User>>() {
                            @Override
                            public void assertion(List<User> actual) throws AssertionError {
                                assertMatch(actual, ADMIN, USER);
                            }
                        }));
    }

    @Test
    void testProjects() throws Exception{
        mockMvc.perform(get("/projects"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("projects"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/projects.jsp"))
                .andExpect(model().attribute("projects", getWithExcess(PROJECTS, SecurityUtil.authUserSumPerDay())));
    }
}
