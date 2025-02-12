package com.Learning.RedisAndJDBC.Controller;

import com.Learning.RedisAndJDBC.Entity.Employee;
import com.Learning.RedisAndJDBC.Service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/read")
    public List<Employee> getAllEmployees() {
        return userService.getAllEmployees();
    }

    @GetMapping("/read/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return userService.getEmployeeById(id);
    }

    @PostMapping("/insert")
    public String addEmployee(@RequestBody Employee employees) {
        userService.addEmployee(employees);
        return "User added successfully";
    }

    @PutMapping("/update/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody Employee employees) {
        employees.setId(id);
        userService.updateEmployee(employees);
        return "User updated successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        userService.deleteEmployee(id);
        return "User deleted successfully";
    }
}

