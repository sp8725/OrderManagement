package com.shubham.ordermanagement.assessment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;

@Configuration
public class JacksonConfig {

	@Bean
	public Module hibernateModule() {
		return new Hibernate6Module();
	}
}
