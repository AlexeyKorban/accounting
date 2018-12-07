package ru.ldwx.accounting.service;

import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.repository.ProjectRepository;
import ru.ldwx.accounting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

import static ru.ldwx.accounting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Project get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public List<Project> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public List<Project> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public void update(Project project, int userId) {
        Assert.notNull(project, "project must not be null");
        checkNotFoundWithId(repository.save(project, userId), project.getId());
    }

    @Override
    public Project create(Project project, int userId) {
        Assert.notNull(project, "project must not be null");
        return repository.save(project, userId);
    }

    @Override
    public Project getWithUser(int id, int userId) {
        return checkNotFoundWithId(repository.getWithUser(id, userId), id);
    }
}
