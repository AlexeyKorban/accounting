package ru.ldwx.accounting.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.model.User;
import ru.ldwx.accounting.repository.ProjectRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaProjectRepositoryImpl implements ProjectRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Project save(Project project, int userId) {
        if (!project.isNew() && get(project.getId(), userId) == null) {
            return null;
        }
        project.setUser(em.getReference(User.class, userId));
        if (project.isNew()) {
            em.persist(project);
            return project;
        } else {
            return em.merge(project);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Project.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Project get(int id, int userId) {
        Project project = em.find(Project.class, id);
        return project != null && project.getUser().getId() == userId ? project : null;
    }

    @Override
    public List<Project> getAll(int userId) {
        return em.createNamedQuery(Project.ALL_SORTED, Project.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Project> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Project.GET_BETWEEN, Project.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).getResultList();
    }
}