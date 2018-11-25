package ru.ldwx.accounting.service;

import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ProjectService {
    Project get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    default List<Project> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }

    List<Project> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    List<Project> getAll(int userId);

    void update(Project project, int userId) throws NotFoundException;

    Project create(Project project, int userId);

    Project getWithUser(int id, int userId);
}
