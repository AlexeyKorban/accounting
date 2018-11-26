package ru.ldwx.accounting.service;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ldwx.accounting.ProjectTestData.*;
import static ru.ldwx.accounting.UserTestData.ADMIN_ID;
import static ru.ldwx.accounting.UserTestData.USER_ID;

public abstract class AbstractProjectServiceTest extends AbstractServiceTest {

    @Autowired
    protected ProjectService service;

    @Test
    void delete() throws Exception {
        service.delete(PROJECT1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), PROJECT6, PROJECT5, PROJECT4, PROJECT3, PROJECT2);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(PROJECT1_ID, 1));
    }

    @Test
    void create() throws Exception {
        Project created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, PROJECT6, PROJECT5, PROJECT4, PROJECT3, PROJECT2, PROJECT1);
    }

    @Test
    void get() throws Exception {
        Project actual = service.get(ADMIN_PROJECT_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_PROJECT1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(PROJECT1_ID, ADMIN_ID));
    }

    @Test
    void update() throws Exception {
        Project updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(PROJECT1_ID, USER_ID), updated);
    }

    @Test
    void updateNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
        service.update(PROJECT1, ADMIN_ID));
    }

    @Test
    void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), PROJECTS);
    }

    @Test
    void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2018, Month.OCTOBER, 26),
                LocalDate.of(2018, Month.OCTOBER, 26), USER_ID), PROJECT2, PROJECT1);
    }

    @Test
    void testValidation() throws Exception {
        Assumptions.assumeTrue(isJpaBased());
        validateRootCause(() -> service.create(new Project(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Project(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Project(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Project(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 5001), USER_ID), ConstraintViolationException.class);
    }
}
