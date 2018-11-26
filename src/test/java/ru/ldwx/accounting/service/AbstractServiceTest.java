package ru.ldwx.accounting.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.ldwx.accounting.ActiveDbProfileResolver;
import ru.ldwx.accounting.Profiles;
import ru.ldwx.accounting.TimingExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ldwx.accounting.util.ValidationUtil.getRootCause;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
abstract public class AbstractServiceTest {

    @Autowired
    public Environment env;

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    boolean isJpaBased() {
        return env.acceptsProfiles(org.springframework.core.env.Profiles.of(Profiles.JPA, Profiles.DATAJPA));
    }

    <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> {
        try {
            runnable.run();
        } catch (Exception e) {
            throw getRootCause(e);
        }});
    }
}
