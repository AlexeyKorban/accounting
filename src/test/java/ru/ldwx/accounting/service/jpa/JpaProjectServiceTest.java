package ru.ldwx.accounting.service.jpa;

import ru.ldwx.accounting.service.AbstractProjectServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static ru.ldwx.accounting.Profiles.JPA;

@ActiveProfiles(JPA)
public class JpaProjectServiceTest extends AbstractProjectServiceTest {
}
