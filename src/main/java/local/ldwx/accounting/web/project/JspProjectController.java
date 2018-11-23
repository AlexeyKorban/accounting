package local.ldwx.accounting.web.project;

import local.ldwx.accounting.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static local.ldwx.accounting.util.DateTimeUtil.parseLocalDate;
import static local.ldwx.accounting.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/projects")
public class JspProjectController extends AbstractProjectController{

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/projects";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("project", super.get(getId(request)));
        return "projectForm";
    }

    @PostMapping
    public String updateOrCreate(HttpServletRequest request) {
        Project project = new Project(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("summ")));
        if (request.getParameter("id").isEmpty()) {
            super.create(project);
        } else {
            super.update(project, getId(request));
        }
        return "redirect:/projects";
    }

    @PostMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("projects", super.getBetween(startDate, startTime, endDate, endTime));
        return "projects";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
