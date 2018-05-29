package com.rbkmoney.provider.googlepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by vpankrashkin on 29.05.18.
 */
@SpringBootApplication(scanBasePackages = "com.rbkmoney.provider.googlepay")
@ServletComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
