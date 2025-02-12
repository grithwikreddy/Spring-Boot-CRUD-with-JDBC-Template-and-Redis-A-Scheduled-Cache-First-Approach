package com.Learning.RedisAndJDBC.Scheduler;
import com.Learning.RedisAndJDBC.Service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DatabaseScheduler {
    private final UserService userService;

    public DatabaseScheduler(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedRate = 3600000) // Runs every 1 hour
    public void updateRedisFromDatabase() {
        System.out.println("Scheduler running: Updating Redis from MySQL...");
        userService.loadDataFromDatabase();
    }
}
