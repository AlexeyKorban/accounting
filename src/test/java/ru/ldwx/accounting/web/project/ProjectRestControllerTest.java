package ru.ldwx.accounting.web.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.service.ProjectService;
import ru.ldwx.accounting.web.AbstractControllerTest;
import ru.ldwx.accounting.web.json.JsonUtil;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ldwx.accounting.ProjectTestData.*;
import static ru.ldwx.accounting.ProjectTestData.assertMatch;
import static ru.ldwx.accounting.TestUtil.*;
import static ru.ldwx.accounting.UserTestData.*;
import static ru.ldwx.accounting.model.AbstractBaseEntity.START_SEQ;
import static ru.ldwx.accounting.util.ProjectsUtil.createWithExcess;
import static ru.ldwx.accounting.util.ProjectsUtil.getWithExcess;
import static ru.ldwx.accounting.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.ldwx.accounting.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_DATETIME;

class ProjectRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProjectRestController.REST_URL + '/';

    @Autowired
    private ProjectService service;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_PROJECT_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Project.class), ADMIN_PROJECT1));
    }

    @Test
    void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + PROJECT1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_PROJECT_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + PROJECT1_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(START_SEQ), PROJECT6, PROJECT5, PROJECT4, PROJECT3, PROJECT2);
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + ADMIN_PROJECT_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testUpdate() throws Exception {
        Project updated = getUpdated();

        mockMvc.perform(put(REST_URL + PROJECT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());

        assertMatch(service.get(PROJECT1_ID, START_SEQ), updated);
    }

    @Test
    void testCreate() throws Exception {
        Project created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)));

        Project returned = readFromJsonResultActions(action, Project.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(ADMIN_ID), created, ADMIN_PROJECT2, ADMIN_PROJECT1);
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getToMatcher(getWithExcess(PROJECTS, USER.getSumPerDay())));
    }

    @Test
    void testFilter() throws Exception {
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDate", "2018-10-27").param("startTime", "10:00")
                .param("endDate", "2018-10-27").param("endTime", "10:10")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(getToMatcher(createWithExcess(PROJECT4, false), createWithExcess(PROJECT3, false)));
    }

    @Test
    void testFilterAll() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=&endTime=")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(getToMatcher(getWithExcess(PROJECTS, USER.getSumPerDay())));
    }

    @Test
    void testCreateInvalid() throws Exception {
        Project invalid = new Project(null, null, "Dummy", 200);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testUpdateInvalid() throws Exception {
        Project invalid = new Project(PROJECT1_ID, null, null, 6000);
        mockMvc.perform(put(REST_URL + PROJECT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testUpdateHtmlUnsafe() throws Exception {
        Project invalid = new Project(PROJECT1_ID, LocalDateTime.now(), "<script>alert(123)</script>", 200);
        mockMvc.perform(put(REST_URL + PROJECT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testUpdateDuplicate() throws Exception {
        Project invalid = new Project(PROJECT1_ID, PROJECT2.getDateTime(), "Dummy", 200);

        mockMvc.perform(put(REST_URL + PROJECT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DATETIME));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testCreateDuplicate() throws Exception {
        Project invalid = new Project(null, ADMIN_PROJECT1.getDateTime(), "Dummy", 200);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DATETIME));
    }
}
