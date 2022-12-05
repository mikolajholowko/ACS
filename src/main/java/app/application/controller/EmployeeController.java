package app.application.controller;

import app.application.model.dto.EmployeeDto;
import app.application.service.EmployeeService;
import org.apache.tomcat.jni.File;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
public class EmployeeController {

    public EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/employee/{id}")
    public EmployeeDto findById(@PathVariable UUID id) {
        return employeeService.getById(id);
    }

    @GetMapping(value = "/employees")
    public List<EmployeeDto> findAllEmployees() {
        return employeeService.getAll();
    }

    @PutMapping(value = "/employee")
    public void save(@RequestBody EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
    }

    @DeleteMapping(value = "/employee/{id}")
    public void deleteById(@PathVariable UUID id) {
        employeeService.deleteById(id);
    }

//    @PostMapping(value = "/employeeqr")
//    public String uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getName());
//        System.out.println(file.getContentType());
//        System.out.println(file.getSize());
//
//        //String Path_Directory = "C:\\Users\\mikol\\Desktop\\Projekt\\03_UserDesign\\QR.png";
//        String Path_Directory = new ClassPathResource("static/image/").getFile().getAbsolutePath();
//        Files.copy(file.getInputStream(), Paths.get(Path_Directory+file.))
//        return "Image has been successfully uploaded";
//    }

}
