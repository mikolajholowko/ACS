package app.application.controller;

import app.application.model.dto.EmployeeDto;
import app.application.model.dto.Role;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration
class QrControllerTest {

    @Test
    void findByEmployeeUUID() {
    }

    @Test
    void findAllQrCodes() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void validateCode() {
//
//
//            EmployeeDto employeeDto = new EmployeeDto("Tomasz", "Spawacz", "t.spawacz@domain.com", Role.ROLE_EMPLOYEE, "password");
//
//            mvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
//                            .contentType(CONTENT_TYPE)
//                            .content(objectMapper.writeValueAsString(employeeDto)))
//                    .andDo(print())
//                    .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void refreshQr() {
    }
}