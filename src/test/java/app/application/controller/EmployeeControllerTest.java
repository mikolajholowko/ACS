package app.application.controller;

import app.application.model.Employee;
import app.application.model.dto.EmployeeDto;
import app.application.model.dto.Role;
import app.application.repository.EmployeeRepository;
import app.application.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
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
    public EmployeeService employeeService;


    @Before
    public EmployeeDto addEmployee() {
        Employee employeeDto = Employee.mapToEntity(employeeService.save(new EmployeeDto("Mikolaj", "Holowko", "m.holowko@acs.com", Role.ROLE_ADMIN, "password")));
        return Employee.mapToDto(employeeDto);

    }


    @Test
    void findById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/employee/{id}", "c0a80069-84fc-1ca0-8184-fc8ca3030000"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("c0a80069-84fc-1ca0-8184-fc8ca3030000"));
    }


    @Test
    void findAllEmployees() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(CONTENT_TYPE))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        Assertions.assertThat(content).isNotBlank();
    }

    @Test
    void save() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto("Tomasz", "Spawacz", "t.spawacz@domain.com", Role.ROLE_EMPLOYEE, "password");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void deleteById() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/static/employee/{id}", "c0a80069-84fd-1d36-8184-fd3d3d8e0001"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testFindById() {
    }

    @Test
    void testFindAllEmployees() {
    }

    @Test
    void testSave() {
    }

    @Test
    void testDeleteById() {
    }

    @Test
    void createEmployee() {
    }
}