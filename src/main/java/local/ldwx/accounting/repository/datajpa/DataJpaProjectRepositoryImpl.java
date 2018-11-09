package local.ldwx.accounting.repository.datajpa;

import local.ldwx.accounting.model.Project;
import local.ldwx.accounting.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaProjectRepositoryImpl implements ProjectRepository {

    @Autowired
    private CrudProjectRepository crudProjectRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    @Transactional
    public Project save(Project project, int userId) {
        if (!project.isNew() && get(project.getId(), userId) == null) {
            return null;
        }
        project.setUser(crudUserRepository.getOne(userId));
        return crudProjectRepository.save(project);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudProjectRepository.delete(id, userId) != 0;
    }

    @Override
    public Project get(int id, int userId) {
        return crudProjectRepository.findById(id).filter(project -> project.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<Project> getAll(int userId) {
        return crudProjectRepository.getAll(userId);
    }

    @Override
    public List<Project> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudProjectRepository.getBetween(startDate, endDate, userId);
    }

    @Override
    public Project getWithUser(int id, int userId) {
        return crudProjectRepository.getWithUser(id, userId);
    }
}
