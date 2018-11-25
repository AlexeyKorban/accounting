package ru.ldwx.accounting;

import ru.ldwx.accounting.model.Project;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.ldwx.accounting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectTestData {
    public static final int PROJECT1_ID = START_SEQ + 2;
    public static final int ADMIN_PROJECT_ID = START_SEQ + 8;

    public static final Project PROJECT1 = new Project(PROJECT1_ID, of(2018, Month.OCTOBER, 26, 10, 0), "МРСК", 500);
    public static final Project PROJECT2 = new Project(PROJECT1_ID + 1, of(2018, Month.OCTOBER, 26, 11, 0), "Ростелеком", 500);
    public static final Project PROJECT3 = new Project(PROJECT1_ID + 2, of(2018, Month.OCTOBER, 27, 10, 0), "ДИЛ", 500);
    public static final Project PROJECT4 = new Project(PROJECT1_ID + 3, of(2018, Month.OCTOBER, 27, 10, 10), "Юнит", 500);
    public static final Project PROJECT5 = new Project(PROJECT1_ID + 4, of(2018, Month.OCTOBER, 27, 11, 0), "Софтланй", 500);
    public static final Project PROJECT6 = new Project(PROJECT1_ID + 5, of(2018, Month.OCTOBER, 27, 11, 10), "Изделия и материалы", 500);
    public static final Project ADMIN_PROJECT1 = new Project(ADMIN_PROJECT_ID, of(2018, Month.OCTOBER, 26, 10, 0), "Проект Админа", 500);
    public static final Project ADMIN_PROJECT2 = new Project(ADMIN_PROJECT_ID + 1, of(2018, Month.OCTOBER, 26, 20, 0), "Проект 2 Админа", 500);

    public static final List<Project> PROJECTS = Arrays.asList(PROJECT6, PROJECT5, PROJECT4, PROJECT3, PROJECT2, PROJECT1);

    public static Project getCreated() {
        return new Project(null, of(2018, Month.OCTOBER, 29, 18, 0), "Созданный проект", 300);
    }

    public static Project getUpdated() {
        return new Project(PROJECT1_ID, PROJECT1.getDateTime(), "Обновленный проект", 200);
    }

    public static void assertMatch(Project actual, Project expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user");
    }

    public static void assertMatch(Iterable<Project> actual, Project... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Project> actual, Iterable<Project> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
    }
}
