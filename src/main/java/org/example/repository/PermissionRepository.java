package org.example.repository;

import org.example.model.Permission;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class PermissionRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PermissionRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Permission> getPermissions(Long role_id) {
        String sql = "select p.* from role_permission rp inner join permission p on rp.permission_id=p.id where rp.role_id=?";
        BeanPropertyRowMapper<Permission> mapper = new BeanPropertyRowMapper<>(Permission.class);
        return jdbcTemplate.query(sql, mapper, role_id);
    }
}
