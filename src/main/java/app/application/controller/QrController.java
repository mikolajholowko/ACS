package app.application.controller;

import app.application.model.dto.EmployeeDto;
import app.application.model.dto.QrDto;
import app.application.service.QrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QrController {

    private final QrService qrService;


    //TODO use ResponseEntity<Dto> instead of Dto

    @GetMapping(value = "/qr/{id}")
    public QrDto findById(@PathVariable UUID id) {
        return qrService.getById(id);
    }

    @GetMapping(value = "/qr")
    public List<QrDto> findAllEmployees() {
        return qrService.getAll();
    }

    @PutMapping(value = "/qr")
    public void save(@RequestBody QrDto qrDto) {
        qrService.save(qrService.save(qrDto));
    }

    @DeleteMapping(value = "/qr/{id}")
    public void deleteById(@PathVariable UUID id) {
        qrService.deleteById(id);
    }

    @PostMapping(value = "/generateQr")
    public String uploadImage(@RequestBody EmployeeDto employeeDto) {
        qrService.generateQrCodeForEmployee(employeeDto);
        return employeeDto.toString();
    }


//    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
//    public ResponseEntity<Resource> image() throws IOException {
//        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
//                "/home/silentsudo/Videos/dum111111b.jpg"
//        )));
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .contentLength(inputStream.contentLength())
//                .body(inputStream);
//
//    }
//
//    @GetMapping(value = "/png", produces = MediaType.IMAGE_JPEG_VALUE)
//    public ResponseEntity<Resource> image() throws IOException {
//        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
//                "C:\\Users\\mikol\\Desktop\\SZKF-main\\outputFile.jpg"
//        )));
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .contentLength(inputStream.contentLength())
//                .body(inputStream);
//
//    }

    @GetMapping(value = "/api/v1/validate-code")
    public ResponseEntity<Integer> validateCode(@RequestBody QrDto qrDto) {
        System.out.println(qrDto);
        return ResponseEntity.status(qrService.qrValidation(qrDto)).body(qrService.qrValidation(qrDto));
    }

}
