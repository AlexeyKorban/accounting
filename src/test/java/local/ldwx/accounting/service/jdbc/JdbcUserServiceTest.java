package local.ldwx.accounting.service.jdbc;

import local.ldwx.accounting.service.AbstractUserServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static local.ldwx.accounting.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}
