package app.application.controller;

import app.application.model.dto.EmployeeDto;
import app.application.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class EmployeeController {

    public EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/api/v1/employee/{id}")
    public EmployeeDto findById(@PathVariable UUID id) {
        return employeeService.getById(id);
    }

    @GetMapping(value = "/api/v1/employees")
    public List<EmployeeDto> findAllEmployees() {
        return employeeService.getAll();
    }

    @PutMapping(value = "/api/v1/employee")
    public void save(@RequestBody EmployeeDto employeeDto) {
        employeeService.changeEmailAndPassword(employeeDto);
    }

    @DeleteMapping(value = "/api/v1/employee/{id}")
    public void deleteById(@PathVariable UUID id) {
        employeeService.deleteById(id);
    }

    @PostMapping(value = "/api/v1/employee")
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.save(employeeDto);
    }

}
