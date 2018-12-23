package ru.ldwx.accounting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.ldwx.accounting.TestUtil;
import ru.ldwx.accounting.model.Role;
import ru.ldwx.accounting.model.User;
import ru.ldwx.accounting.to.UserTo;
import ru.ldwx.accounting.util.UserUtil;
import ru.ldwx.accounting.web.AbstractControllerTest;
import ru.ldwx.accounting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ldwx.accounting.UserTestData.*;
import static ru.ldwx.accounting.web.user.ProfileRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Test
    void testGet() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(REST_URL))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(getUserMatcher(USER))
        );
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER), updatedTo));
    }
}
