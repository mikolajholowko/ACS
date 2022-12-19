package app.application.model.dto;

import app.application.model.Employee;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class EmployeeDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String password;

    private String login;

    public EmployeeDto(String firstName, String lastName, String email, Role role, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.login = login;
        this.password = password;
    }
}
