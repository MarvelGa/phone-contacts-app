package com.contacts.phone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@RestController
public class PhoneContactsApplication {

    @GetMapping("/")
    String home() {
        return "Welcome to Phone Contacts Project!";
    }

    public static void main(String[] args) {
        SpringApplication.run(PhoneContactsApplication.class, args);
    }
}
