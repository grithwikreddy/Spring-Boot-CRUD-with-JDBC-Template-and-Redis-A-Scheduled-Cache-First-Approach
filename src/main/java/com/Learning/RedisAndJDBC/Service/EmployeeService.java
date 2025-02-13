package com.Learning.RedisAndJDBC.Service;

import com.Learning.RedisAndJDBC.Entity.Employee;
import com.Learning.RedisAndJDBC.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RedisTemplate<String, Employee> redisTemplate;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, RedisTemplate<String, Employee> redisTemplate) {
        this.employeeRepository = employeeRepository;
        this.redisTemplate = redisTemplate;
    }
    public List<String> getAllKeysUsingScan() {
        List<String> keys = new ArrayList<>();
        try (Cursor<byte[]> cursor = redisTemplate.getConnectionFactory()
                .getConnection().scan(ScanOptions.scanOptions().match("*").count(1000).build())) {
            while (cursor.hasNext()) {
                keys.add(new String(cursor.next())); // Convert bytes to String
            }
        }
        return keys;
    }
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        List<String> stringKeys = getAllKeysUsingScan();

        for (String key : stringKeys) {
            DataType type = redisTemplate.type(key);
            if (type != DataType.STRING) {
                System.out.println("Skipping key " + key + " - Not a STRING (Found: " + type + ")");
                continue;
            }
            Object value = redisTemplate.opsForValue().get(key);
            if (value instanceof Employee) {
                employees.add((Employee) value);
            } else {
                System.out.println("Skipping key " + key + " - Not an Employee object.");
            }
        }
        return employees;
    }

    public Employee getEmployeeById(String id) {
        return redisTemplate.opsForValue().get(id);
    }

    public void addEmployee(Employee employee) {
        redisTemplate.opsForValue().set(employee.getId(), employee);
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employees) {
        employeeRepository.update(employees);
        redisTemplate.delete(employees.getId());
        redisTemplate.opsForValue().set(employees.getId(), employees);
    }

    public void deleteEmployee(String id) {
        employeeRepository.delete(id);
        redisTemplate.delete(id);
    }

    public void loadDataFromDatabase() {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            redisTemplate.opsForValue().set(employee.getId(), employee);
        }
        System.out.println("Redis reloaded with data from MySQL!");
    }
}


