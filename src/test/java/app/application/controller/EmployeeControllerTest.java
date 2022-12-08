package app.application.controller;

import app.application.QrApp;
import app.application.model.Employee;
import app.application.model.dto.EmployeeDto;
import app.application.model.dto.Role;
import app.application.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = QrApp.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private EmployeeRepository employeeService;


    public EmployeeDto addEmployee() {
        Employee employeeDto = employeeService.save(new Employee("Mikołaj", "Hołowko", "mholowko", "mholowko", "email", Role.ROLE_ADMIN));

        return Employee.mapToDto(employeeDto);

    }


    @Test
    void findById() {

        EmployeeDto employee = addEmployee();

        mvc.perform(get("/employee/{" + employee.getId() + "}")
                .
    }

}

    @Test
    void findAllEmployees() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }
}