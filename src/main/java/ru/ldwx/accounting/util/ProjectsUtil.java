package ru.ldwx.accounting.util;

import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.to.ProjectTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ProjectsUtil {
    public static final int DEFAULT_SUM_PER_DAY = 2_000;

    public static List<ProjectTo> getWithExcess(Collection<Project> projects, int sumPerDay) {
        return getFilteredWithExcess(projects, sumPerDay, project -> true);
    }

    public static List<ProjectTo> getFilteredWithExcess(Collection<Project> projects, int sumPerDay, LocalTime startTime, LocalTime endTime) {
        return getFilteredWithExcess(projects, sumPerDay, project -> Util.isBetween(project.getTime(), startTime, endTime));
    }

    private static List<ProjectTo> getFilteredWithExcess(Collection<Project> projects, int sumPerDay, Predicate<Project> filter) {
        Map<LocalDate, Integer> mapSumPerDay = projects.stream()
                .collect(
                        Collectors.groupingBy(Project::getDate, Collectors.summingInt(Project::getSum))
                );

        return projects.stream()
                .filter(filter)
                .map(project -> createWithExcess(project, mapSumPerDay.get(project.getDate()) > sumPerDay))
                .collect(toList());
    }

    public static ProjectTo createWithExcess(Project project, boolean Excess) {
        return new ProjectTo(project.getId(), project.getDateTime(), project.getDescription(), project.getSum(), Excess);
    }
}
