package com.Learning.RedisAndJDBC.Scheduler;
import com.Learning.RedisAndJDBC.Service.EmployeeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DatabaseScheduler {
    private final EmployeeService employeeService;

    public DatabaseScheduler(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Scheduled(fixedRate = 3600000) // Runs every 1 hour
    public void updateRedisFromDatabase() {
        System.out.println("Scheduler running: Updating Redis from MySQL...");
        employeeService.loadDataFromDatabase();
    }
}
