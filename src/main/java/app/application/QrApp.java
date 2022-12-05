package app.application;

import app.application.service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
@RequiredArgsConstructor
public class QrApp {

    public static void main(String[] args) {
        SpringApplication.run(QrApp.class, args);
    }

}
