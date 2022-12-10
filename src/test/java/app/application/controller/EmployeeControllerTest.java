package app.application.controller;

import app.application.QrApp;
import app.application.model.Employee;
import app.application.model.dto.EmployeeDto;
import app.application.model.dto.Role;
import app.application.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.util.UUID;



@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = QrApp.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository employeeService;


    @Before
    public EmployeeDto addEmployee() {
        Employee employeeDto = employeeService.save(new Employee("Mikołaj", "Hołowko", "mholowko", "mholowko", "email", Role.ROLE_ADMIN));

        return Employee.mapToDto(employeeDto);

    }

    EmployeeDto employee = addEmployee();
    @Test
    void findById() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/employee/{id}")
                        .param("id", "c0a80069-84fc-1745-8184-fc6748820000"))
                        .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect()



        //mvc.perform(get("/employee/{}" + employee.getId()
//        mvc.perform(get("/employee/{}")
//                .andExpectAll

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