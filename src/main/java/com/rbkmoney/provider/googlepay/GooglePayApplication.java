package com.rbkmoney.provider.googlepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = "com.rbkmoney.provider.googlepay")
public class GooglePayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GooglePayApplication.class, args);
    }
}
