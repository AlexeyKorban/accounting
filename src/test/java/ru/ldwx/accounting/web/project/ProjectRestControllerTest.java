package ru.ldwx.accounting.web.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.service.ProjectService;
import ru.ldwx.accounting.web.AbstractControllerTest;
import ru.ldwx.accounting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ldwx.accounting.ProjectTestData.*;
import static ru.ldwx.accounting.TestUtil.readFromJsonMvcResult;
import static ru.ldwx.accounting.TestUtil.readFromJsonResultActions;
import static ru.ldwx.accounting.UserTestData.USER;
import static ru.ldwx.accounting.model.AbstractBaseEntity.START_SEQ;
import static ru.ldwx.accounting.util.ProjectsUtil.createWithExcess;
import static ru.ldwx.accounting.util.ProjectsUtil.getWithExcess;

class ProjectRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProjectRestController.REST_URL + '/';

    @Autowired
    private ProjectService service;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + PROJECT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Project.class), PROJECT1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + PROJECT1_ID))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(START_SEQ), PROJECT6, PROJECT5, PROJECT4, PROJECT3, PROJECT2);
    }

    @Test
    void testUpdate() throws Exception {
        Project updated = getUpdated();

        mockMvc.perform(put(REST_URL + PROJECT1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(service.get(PROJECT1_ID, START_SEQ), updated);
    }

    @Test
    void testCreate() throws Exception {
        Project created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)));

        Project returned = readFromJsonResultActions(action, Project.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(START_SEQ), created, PROJECT6, PROJECT5, PROJECT4, PROJECT3, PROJECT2, PROJECT1);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getToMatcher(getWithExcess(PROJECTS, USER.getSumPerDay())));
    }

    @Test
    void testFilter() throws Exception {
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDate", "2018-10-27").param("startTime", "10:00")
                .param("endDate", "2018-10-27").param("endTime", "10:10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(getToMatcher(createWithExcess(PROJECT4, false), createWithExcess(PROJECT3, false)));
    }

    @Test
    void testFilterAll() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=&endTime="))
                .andExpect(status().isOk())
                .andExpect(getToMatcher(getWithExcess(PROJECTS, USER.getSumPerDay())));
    }
}
