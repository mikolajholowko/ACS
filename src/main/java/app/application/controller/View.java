package app.application.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class View {

    public View() {
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/employee")
    public String employee() {
        return "employee.html";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

}
