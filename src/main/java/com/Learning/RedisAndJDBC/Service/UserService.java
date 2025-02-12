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

    public List<Employee> getAllEmployees() {
        List<Employee> employees = (List<Employee>) redisTemplate.opsForValue().get(REDIS_KEY);
        if (employees == null) {
            employees = userRepository.findAll();
            redisTemplate.opsForValue().set(REDIS_KEY, employees);
        }
        return employees;
    }

    public Employee getEmployeeById(int id) {
        return userRepository.findById(id);
    }

    public void addEmployee(Employee employee) {
        userRepository.save(employee);
        redisTemplate.delete(REDIS_KEY);
    }

    public void updateEmployee(Employee employees) {
        userRepository.update(employees);
        redisTemplate.delete(REDIS_KEY);
    }

    public void deleteEmployee(int id) {
        userRepository.delete(id);
        redisTemplate.delete(REDIS_KEY);
    }
    public void loadDataFromDatabase() {
        List<Employee> employees = userRepository.findAll();
        redisTemplate.opsForValue().set(REDIS_KEY, employees);
        System.out.println("Redis reloaded with data from MySQL!");
    }
}

