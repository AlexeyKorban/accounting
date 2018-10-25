package local.ldwx.accounting.web.project;

import local.ldwx.accounting.model.Project;
import local.ldwx.accounting.service.ProjectService;
import local.ldwx.accounting.to.ProjectTo;
import local.ldwx.accounting.util.DateTimeUtil;
import local.ldwx.accounting.util.ProjectsUtil;
import local.ldwx.accounting.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static local.ldwx.accounting.util.ValidationUtil.assureIdConsistent;
import static local.ldwx.accounting.util.ValidationUtil.checkNew;

@Controller
public class ProjectRestController {
    private static final Logger log = LoggerFactory.getLogger(ProjectRestController.class);
    
    private final ProjectService service;

    @Autowired
    public ProjectRestController(ProjectService service) {
        this.service = service;
    }

    public Project get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get project {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete project {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<ProjectTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return ProjectsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserSumPerDay());
    }

    public Project create(Project meal) {
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        log.info("create {} for user {}", meal, userId);
        return service.create(meal, userId);
    }

    public void update(Project meal, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public List<ProjectTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Project> mealsDateFiltered = service.getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId);

        return ProjectsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserSumPerDay(),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX
        );
    }
}
