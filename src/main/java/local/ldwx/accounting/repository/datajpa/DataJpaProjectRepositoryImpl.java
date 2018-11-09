package local.ldwx.accounting.repository.datajpa;

import local.ldwx.accounting.model.Project;
import local.ldwx.accounting.repository.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaProjectRepositoryImpl implements ProjectRepository {
    @Override
    public Project save(Project project, int userId) {
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return false;
    }

    @Override
    public Project get(int id, int userId) {
        return null;
    }

    @Override
    public List<Project> getAll(int userId) {
        return null;
    }

    @Override
    public List<Project> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}
