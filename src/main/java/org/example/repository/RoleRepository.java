package org.example.repository;

import org.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

public class RoleRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Role> getRole(Long userId) {
        String sql = "select r.* from authuser_role ar inner join role r on ar.role_id=r.id where ar.authuser_id=?";
        BeanPropertyRowMapper<Role> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Role.class);
        return jdbcTemplate.query(sql, beanPropertyRowMapper, userId);
    }

    public void addRole(Long userId, Role role) {

    }
}
