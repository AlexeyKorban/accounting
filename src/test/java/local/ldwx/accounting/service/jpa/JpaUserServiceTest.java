package local.ldwx.accounting.service.jpa;

import local.ldwx.accounting.service.AbstractJpaUserServiceTest;
import local.ldwx.accounting.service.AbstractUserServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static local.ldwx.accounting.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractJpaUserServiceTest {
}
