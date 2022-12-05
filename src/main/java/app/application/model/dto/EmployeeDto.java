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

}
