package app.application.model.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeValidationResult {

    UUID employeeId;
    Role role;
    String description;


}
