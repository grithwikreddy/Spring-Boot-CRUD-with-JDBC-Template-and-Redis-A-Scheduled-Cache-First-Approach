package com.Learning.RedisAndJDBC;

import com.Learning.RedisAndJDBC.Service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedisAndJdbcApplication implements CommandLineRunner {
	private final EmployeeService employeeService; // Inject UserService

	public RedisAndJdbcApplication(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisAndJdbcApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("Loading data from MySQL into Redis at startup...");
		employeeService.loadDataFromDatabase(); // Corrected method call
	}
}

