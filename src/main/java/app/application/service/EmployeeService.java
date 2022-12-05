package app.application.service;

import app.application.model.Employee;
import app.application.model.dto.EmployeeDto;
import app.application.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    public EmployeeDto getById(UUID id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return Employee.mapToDto(employee.get());
        } else {
            throw new RuntimeException("User with id: [" + id + "] not found.");
        }
    }

    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll().stream().map(Employee::mapToDto).collect(Collectors.toList());
    }

    public void deleteById(UUID id) {
        employeeRepository.findById(id).ifPresent(employee -> employeeRepository.deleteById(id));
    }

    public EmployeeDto save(EmployeeDto employeeDto) {
        return Employee.mapToDto(employeeRepository.save(Employee.mapToEntity(employeeDto)));
    }


}
