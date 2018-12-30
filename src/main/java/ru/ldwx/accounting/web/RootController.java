package ru.ldwx.accounting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ldwx.accounting.service.ProjectService;
import ru.ldwx.accounting.util.ProjectsUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String root() {
        return "redirect:projects";
    }

    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects",
                ProjectsUtil.getWithExcess(projectService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserSumPerDay()));
        return "projects";
    }
}
