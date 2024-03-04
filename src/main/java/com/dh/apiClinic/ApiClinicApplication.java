package com.dh.apiClinic;
import org.apache.log4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class ApiClinicApplication {

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        SpringApplication.run(ApiClinicApplication.class, args);
    }

}
