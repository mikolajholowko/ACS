package app.application.controller;

import app.application.model.dto.EmployeeDto;
import app.application.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@AllArgsConstructor
public class View {

    private final EmployeeService employeeService;


    @GetMapping("/")
    public String index() {
        return "login.html";
    }

    @GetMapping("/employee")
    public String employee() {
        return "employee/index.html";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }


    @GetMapping("/admin-account-update")
    public String adminAccountUpdate() {
        return "admin/accountUpdate.html";
    }

    @GetMapping("/admin-create-account")
    public String createAccount() {
        return "admin/createAccount.html";
    }

    @GetMapping("/admin-available-rooms")
    public String adminAvailableRooms() {
        return "admin/availableRooms.html";
    }

    @GetMapping("/employee-available-rooms")
    public String employeeAvailableRooms() {
        return "employee/availableRooms.html";
    }


    @GetMapping("/employee-account-update")
    public String employeeAccountUpdate() {
        return "employee/accountUpdate.html";
    }


    @GetMapping("/auth/employee")
    @ResponseBody
    public EmployeeDto currentUserName(Authentication authentication) {
        return employeeService.getByEmail(authentication.getName());
    }

}
