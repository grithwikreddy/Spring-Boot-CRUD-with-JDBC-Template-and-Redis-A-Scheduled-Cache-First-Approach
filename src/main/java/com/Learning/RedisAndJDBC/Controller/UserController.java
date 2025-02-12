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
    public List<Employee> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/read/{id}")
    public Employee getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/insert")
    public String addUser(@RequestBody Employee user) {
        userService.addUser(user);
        return "User added successfully";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable int id, @RequestBody Employee user) {
        user.setId(id);
        userService.updateUser(user);
        return "User updated successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}

