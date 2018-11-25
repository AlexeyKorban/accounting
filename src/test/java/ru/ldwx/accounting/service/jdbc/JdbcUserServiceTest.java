package ru.ldwx.accounting.service.jdbc;

import ru.ldwx.accounting.service.AbstractUserServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static ru.ldwx.accounting.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}
