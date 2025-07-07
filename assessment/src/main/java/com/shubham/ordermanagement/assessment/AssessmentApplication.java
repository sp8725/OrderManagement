package com.shubham.ordermanagement.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.shubham.ordermanagement.assessment")
@EnableAsync
public class AssessmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}
}
