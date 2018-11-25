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

    public static List<ProjectTo> getWithExcess(Collection<Project> projects, int caloriesPerDay) {
        return getFilteredWithExcess(projects, caloriesPerDay, project -> true);
    }

    public static List<ProjectTo> getFilteredWithExcess(Collection<Project> projects, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return getFilteredWithExcess(projects, caloriesPerDay, meal -> Util.isBetween(meal.getTime(), startTime, endTime));
    }

    private static List<ProjectTo> getFilteredWithExcess(Collection<Project> projects, int caloriesPerDay, Predicate<Project> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = projects.stream()
                .collect(
                        Collectors.groupingBy(Project::getDate, Collectors.summingInt(Project::getSum))
                );

        return projects.stream()
                .filter(filter)
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    public static ProjectTo createWithExcess(Project meal, boolean Excess) {
        return new ProjectTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getSum(), Excess);
    }
}
