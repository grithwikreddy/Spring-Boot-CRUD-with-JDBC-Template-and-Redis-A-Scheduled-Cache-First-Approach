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
        return jdbcTemplate.query("SELECT * FROM users",
                (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("email"))
        );
    }

    public Employee findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("email"))
        );
    }

    public int save(Employee user) {
        return jdbcTemplate.update("INSERT INTO users (name, email) VALUES (?, ?)",
                user.getName(), user.getEmail());
    }

    public int update(Employee user) {
        return jdbcTemplate.update("UPDATE users SET name = ?, email = ? WHERE id = ?",
                user.getName(), user.getEmail(), user.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }
}
