package com.huawei;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.huawei")
public class ProductionPlanningApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductionPlanningApplication.class, args);
    }
}
