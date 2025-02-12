package com.Learning.RedisAndJDBC.Service;

import com.Learning.RedisAndJDBC.Entity.Employee;
import com.Learning.RedisAndJDBC.Repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REDIS_KEY = "redis_key";

    public UserService(UserRepository userRepository, RedisTemplate<String, Object> redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Employee> getAllUsers() {
        List<Employee> users = (List<Employee>) redisTemplate.opsForValue().get(REDIS_KEY);
        if (users == null) {
            users = userRepository.findAll();
            redisTemplate.opsForValue().set(REDIS_KEY, users);
        }
        return users;
    }

    public Employee getUserById(int id) {
        return userRepository.findById(id);
    }

    public void addUser(Employee user) {
        userRepository.save(user);
        redisTemplate.delete(REDIS_KEY);
    }

    public void updateUser(Employee user) {
        userRepository.update(user);
        redisTemplate.delete(REDIS_KEY);
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
        redisTemplate.delete(REDIS_KEY);
    }
    public void loadDataFromDatabase() {
        List<Employee> users = userRepository.findAll();
        redisTemplate.opsForValue().set(REDIS_KEY, users);
        System.out.println("Redis reloaded with data from MySQL!");
    }
}

