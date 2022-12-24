package app.application.service;

import app.application.exception.ACSException;
import app.application.model.Employee;
import app.application.model.dto.EmployeeDto;
import app.application.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final QrService qrService;


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
        employeeDto.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        Employee save = employeeRepository.save(Employee.mapToEntity(employeeDto));
        qrService.generateQrCodeForEmployee(save.getId());
        return Employee.mapToDto(save);
    }

    public EmployeeDto getByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .map(Employee::mapToDto)
                .orElseThrow(() -> {
                    String exMsg = String.format("User with email: [%s] does not found!", email);
                    return new ACSException(exMsg);
                });
    }


}
