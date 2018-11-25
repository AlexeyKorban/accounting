package ru.ldwx.accounting.web;

import ru.ldwx.accounting.service.ProjectService;
import ru.ldwx.accounting.service.UserService;
import ru.ldwx.accounting.util.ProjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:projects";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects",
                ProjectsUtil.getWithExcess(projectService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserSumPerDay()));
        return "projects";
    }
}
