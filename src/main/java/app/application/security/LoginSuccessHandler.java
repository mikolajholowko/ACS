package app.application.security;

import app.application.model.dto.EmployeeDto;
import app.application.model.dto.Role;
import app.application.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final EmployeeService employeeService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        EmployeeDto employeeDto = employeeService.getByEmail((String) authentication.getPrincipal());

        EmployeesDetails employeesDetails = new EmployeesDetails(employeeDto.getEmail(), employeeDto.getPassword(), employeeDto.getRole());

        String redirectURL = request.getContextPath();

        if (employeesDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()))) {
            redirectURL = "/admin";
        } else if (employeesDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_EMPLOYEE.name()))) {
            redirectURL = "/employee";
        }
        response.sendRedirect(redirectURL);

    }

}