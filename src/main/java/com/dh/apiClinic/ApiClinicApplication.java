package com.dh.apiClinic;
import com.dh.apiClinic.config.MultipartConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Slf4j
@ComponentScan
@SpringBootApplication
@Import(MultipartConfiguration.class)
public class ApiClinicApplication {

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        SpringApplication.run(ApiClinicApplication.class, args);
        log.info("La aplicación de la Clínica API ha iniciado correctamente.");
    }

}
