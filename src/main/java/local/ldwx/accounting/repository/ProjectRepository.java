package local.ldwx.accounting.repository;

import local.ldwx.accounting.model.Project;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjectRepository {

    Project save(Project project, int userId);

    // false if project do not belong to userId
    boolean delete(int id, int userId);

    // null if project do not belong to userId
    Project get(int id, int userId);

    // ORDERED dateTime desc
    List<Project> getAll(int userId);

    // ORDERED dateTime desc
    List<Project> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    default Project getWithUser(int id, int userId) {
        throw new UnsupportedOperationException();
    }
}
