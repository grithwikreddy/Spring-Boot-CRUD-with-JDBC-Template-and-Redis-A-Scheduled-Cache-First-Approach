package com.Learning.RedisAndJDBC;

import com.Learning.RedisAndJDBC.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedisAndJdbcApplication implements CommandLineRunner {
	private final UserService userService; // Inject UserService

	public RedisAndJdbcApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisAndJdbcApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("Loading data from MySQL into Redis at startup...");
		userService.loadDataFromDatabase(); // Corrected method call
	}
}

