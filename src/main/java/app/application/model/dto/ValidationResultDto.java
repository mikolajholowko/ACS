package app.application.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ValidationResultDto {

    private int statusCode;
    private EmployeeValidationResult employeeValidationResult;

}
