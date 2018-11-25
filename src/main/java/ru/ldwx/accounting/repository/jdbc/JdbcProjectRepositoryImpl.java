package ru.ldwx.accounting.repository.jdbc;

import ru.ldwx.accounting.model.Project;
import ru.ldwx.accounting.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcProjectRepositoryImpl implements ProjectRepository {

     private static final RowMapper<Project> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Project.class);

     private final JdbcTemplate jdbcTemplate;

     private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

     private final SimpleJdbcInsert insertProject;

     @Autowired
    public JdbcProjectRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
         this.insertProject = new SimpleJdbcInsert(jdbcTemplate)
                 .withTableName("projects")
                 .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public Project save(Project project, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", project.getId())
                .addValue("description", project.getDescription())
                .addValue("sum", project.getSum())
                .addValue("date_time", project.getDateTime())
                .addValue("user_id", userId);

        if (project.isNew()) {
            Number newId = insertProject.executeAndReturnKey(map);
            project.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                    "UPDATE projects " +
                    "   SET description=:description, sum=:sum, date_time=:date_time " +
                    " WHERE id=:id AND user_id=:user_id", map) == 0) {
                return null;
            }
        }
        return project;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM projects WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Project get(int id, int userId) {
         List<Project> projects = jdbcTemplate.query("SELECT * FROM projects WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(projects);
    }

    @Override
    public List<Project> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM projects WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Project> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM projects WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDate, endDate);
    }
}
