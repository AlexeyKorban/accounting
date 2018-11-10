package local.ldwx.accounting.web;

import local.ldwx.accounting.Profiles;
import local.ldwx.accounting.model.Project;
import local.ldwx.accounting.web.project.ProjectRestController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static local.ldwx.accounting.util.DateTimeUtil.parseLocalDate;
import static local.ldwx.accounting.util.DateTimeUtil.parseLocalTime;

public class ProjectServlet extends HttpServlet {

    private ConfigurableApplicationContext springContext;
    private ProjectRestController projectController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);
        springContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        springContext.refresh();
        projectController = springContext.getBean(ProjectRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            Project project = new Project(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                projectController.create(project);
            } else {
                projectController.update(project, getId(request));
            }
            response.sendRedirect("projects");

        } else if ("filter".equals(action)) {
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
            request.setAttribute("projects", projectController.getBetween(startDate, startTime, endDate, endTime));
            request.getRequestDispatcher("/projects.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                projectController.delete(id);
                response.sendRedirect("projects");
                break;
            case "create":
            case "update":
                final Project project = "create".equals(action) ?
                        new Project(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        projectController.get(getId(request));
                request.setAttribute("project", project);
                request.getRequestDispatcher("/projectForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("projects", projectController.getAll());
                request.getRequestDispatcher("/projects.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
