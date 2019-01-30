package ru.ldwx.accounting.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import ru.ldwx.accounting.UserTestData;
import ru.ldwx.accounting.util.exception.ErrorType;
import ru.ldwx.accounting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ldwx.accounting.Profiles.HEROKU;
import static ru.ldwx.accounting.TestUtil.userHttpBasic;
import static ru.ldwx.accounting.UserTestData.ADMIN;
import static ru.ldwx.accounting.UserTestData.USER;
import static ru.ldwx.accounting.UserTestData.USER_ID;
import static ru.ldwx.accounting.util.exception.ModificationRestrictionException.EXCEPTION_MODIFICATION_RESTRICTION;

@ActiveProfiles({HEROKU})
public class HerokuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(errorType(ErrorType.APP_ERROR))
                .andExpect(detailMessage(EXCEPTION_MODIFICATION_RESTRICTION))
                .andExpect(status().isUnavailableForLegalReasons());
    }

    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(UserTestData.jsonWithPassword(USER, "password")))
                .andExpect(errorType(ErrorType.APP_ERROR))
                .andExpect(detailMessage(EXCEPTION_MODIFICATION_RESTRICTION))
                .andExpect(status().isUnavailableForLegalReasons());
    }
}
