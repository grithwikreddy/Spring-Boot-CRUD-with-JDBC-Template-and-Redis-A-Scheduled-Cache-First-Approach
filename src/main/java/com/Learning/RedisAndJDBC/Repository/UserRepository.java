package com.Learning.RedisAndJDBC.Repository;

import com.Learning.RedisAndJDBC.Entity.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employees",
                (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("department"))
        );
    }

    public Employee findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM employees WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("department"))
        );
    }

    public int save(Employee user) {
        return jdbcTemplate.update("INSERT INTO employees (name, department) VALUES (?, ?)",
                user.getName(), user.getDepartment());
    }

    public int update(Employee user) {
        return jdbcTemplate.update("UPDATE employees SET name = ?, department = ? WHERE id = ?",
                user.getName(), user.getDepartment(), user.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM employees WHERE id = ?", id);
    }
}
