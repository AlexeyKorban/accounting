package ru.ldwx.accounting.service.jdbc;

import ru.ldwx.accounting.service.AbstractProjectServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static ru.ldwx.accounting.Profiles.JDBC;

@ActiveProfiles(JDBC)
class JdbcProjectServiceTest extends AbstractProjectServiceTest {
}
