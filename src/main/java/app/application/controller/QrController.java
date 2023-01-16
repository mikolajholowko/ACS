package app.application.controller;

import app.application.model.Qr;
import app.application.model.dto.EmployeeValidationResult;
import app.application.model.dto.QrDto;
import app.application.model.dto.ValidationResultDto;
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
    public ResponseEntity<byte[]> findByEmployeeUUID(@PathVariable UUID id) {
        return ResponseEntity.ok(qrService.getByEmployeeId(id));
    }

    @GetMapping(value = "/qr")
    public List<QrDto> findAllQrCodes() {
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

    @GetMapping(value = "/validate-code")
    public ResponseEntity<EmployeeValidationResult> validateCode(@RequestBody QrDto qrDto) {

        ValidationResultDto validationResult = qrService.qrValidation(qrDto);

        log.info(validationResult.getEmployeeValidationResult().toString());

        return ResponseEntity.status(validationResult.getStatusCode()).body(validationResult.getEmployeeValidationResult());
    }

    @GetMapping(value = "/refreshQr/{id}")
    public ResponseEntity<QrDto> refreshQr(@PathVariable UUID id) {
        return ResponseEntity.ok(Qr.mapToDto(qrService.refreshQrForEmployee(id)));
    }

}
