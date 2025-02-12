package com.Learning.RedisAndJDBC.Service;

import com.Learning.RedisAndJDBC.Entity.Employee;
import com.Learning.RedisAndJDBC.Repository.EmployeeRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REDIS_KEY = "redis_key";

    public EmployeeService(EmployeeRepository employeeRepository, RedisTemplate<String, Object> redisTemplate) {
        this.employeeRepository = employeeRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = (List<Employee>) redisTemplate.opsForValue().get(REDIS_KEY);
        if (employees == null) {
            employees = employeeRepository.findAll();
            redisTemplate.opsForValue().set(REDIS_KEY, employees);
        }
        return employees;
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
        redisTemplate.delete(REDIS_KEY);
    }

    public void updateEmployee(Employee employees) {
        employeeRepository.update(employees);
        redisTemplate.delete(REDIS_KEY);
    }

    public void deleteEmployee(int id) {
        employeeRepository.delete(id);
        redisTemplate.delete(REDIS_KEY);
    }
    public void loadDataFromDatabase() {
        List<Employee> employees = employeeRepository.findAll();
        redisTemplate.opsForValue().set(REDIS_KEY, employees);
        System.out.println("Redis reloaded with data from MySQL!");
    }
}

