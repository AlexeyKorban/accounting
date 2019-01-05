package ru.ldwx.accounting.web;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.ldwx.accounting.ProjectTestData.PROJECTS;
import static ru.ldwx.accounting.TestUtil.userAuth;
import static ru.ldwx.accounting.UserTestData.ADMIN;
import static ru.ldwx.accounting.UserTestData.USER;
import static ru.ldwx.accounting.util.ProjectsUtil.getWithExcess;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    void testUnAuth() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void testProjects() throws Exception {
        mockMvc.perform(get("/projects")
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(view().name("projects"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/projects.jsp"));
    }
}
