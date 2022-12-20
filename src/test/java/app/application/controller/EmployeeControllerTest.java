package app.application.controller;

import app.application.model.Employee;
import app.application.model.dto.EmployeeDto;
import app.application.model.dto.Role;
import app.application.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration
class EmployeeControllerTest {
    private static final String CONTENT_TYPE = "application/json";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public EmployeeRepository employeeService;


//    @Before
//    public EmployeeDto addEmployee() {
//        Employee employeeDto = employeeService.save(new Employee("Mikołaj", "Hołowko", "mholowko", "mholowko", "email", Role.ROLE_ADMIN));
//
//        return Employee.mapToDto(employeeDto);
//
//    }


    @Test
    void findById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/static/employee/{id}", "c0a80069-84fc-1ca0-8184-fc8ca3030000"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("c0a80069-84fc-1ca0-8184-fc8ca3030000"));
    }




    @Test
    void findAllEmployees() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE));
    }

    @Test
    void save() throws Exception {
        EmployeeDto employeeDto = Employee.mapToDto(new Employee("Tomasz", "Spawacz", "aaabbb", "aaabbb", "email", Role.ROLE_ADMIN));
        mvc.perform(MockMvcRequestBuilders.delete("/static/employee/{id}")

                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(employeeDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

//

//
//
//                                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name").value("aaa"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name").value("bbb"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("aaabbb"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("aaabbb"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.qr_id").value(""));
    }

    @Test
    void deleteById() throws Exception{

        mvc.perform(MockMvcRequestBuilders.delete("/static/employee/{id}", "c0a80069-84fd-1d36-8184-fd3d3d8e0001"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    void isIdEnteredRoom{
//
//    }



}