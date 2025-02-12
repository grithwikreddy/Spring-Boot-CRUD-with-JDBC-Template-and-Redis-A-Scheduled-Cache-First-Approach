package com.Learning.RedisAndJDBC.Controller;

import com.Learning.RedisAndJDBC.Entity.Employee;
import com.Learning.RedisAndJDBC.Service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/read")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/read/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/insert")
    public String addEmployee(@RequestBody Employee employees) {
        employeeService.addEmployee(employees);
        return "User added successfully";
    }

    @PutMapping("/update/{id}")
    public String updateEmployee(@PathVariable int id, @RequestBody Employee employees) {
        employees.setId(id);
        employeeService.updateEmployee(employees);
        return "User updated successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return "User deleted successfully";
    }
}

