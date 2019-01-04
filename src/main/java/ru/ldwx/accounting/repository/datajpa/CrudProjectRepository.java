package ru.ldwx.accounting.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.ldwx.accounting.model.Project;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudProjectRepository extends JpaRepository<Project, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Project p WHERE p.id=:id AND p.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Project save(Project project);

    @Query("SELECT p FROM Project p WHERE p.user.id=:userId ORDER BY p.dateTime DESC")
    List<Project> getAll(@Param("userId") int userId);

    @Query("SELECT p FROM Project p WHERE p.user.id=:userId AND p.dateTime BETWEEN :startDate AND :endDate ORDER BY p.dateTime DESC")
    List<Project> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Query("SELECT p FROM Project p JOIN FETCH p.user WHERE p.id = ?1 and p.user.id = ?2")
    Project getWithUser(int id, int userId);
}