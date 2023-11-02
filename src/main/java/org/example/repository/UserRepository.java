package org.example.repository;

import org.example.model.AuthUser;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public UserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(AuthUser authUser) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into authUser(username,password,role,isBlocked) values (?,?,?,?)";
        jdbcTemplate.update(sql, authUser.getUsername(), authUser.getPassword(), authUser.getRole(), authUser.getIsBlocked());
    }

    public void delete(Long id) {
        String sql = "delete from authUser where id=?";
        jdbcTemplate.update(sql, id);
    }

    public void delete(String username) {
        String sql = "delete from authUser where username=?";
        jdbcTemplate.update(sql, username);
    }

    public AuthUser findById(Integer id) {
        String sql = "select * from authUser where id=?";
        BeanPropertyRowMapper<AuthUser> beanPropertyRowMapper = new BeanPropertyRowMapper<>(AuthUser.class);
        return jdbcTemplate.queryForObject(sql, beanPropertyRowMapper, id);
    }

    public AuthUser findByName(String name) {
        String sql = "select * from authUser where username=?";
        BeanPropertyRowMapper<AuthUser> beanPropertyRowMapper = new BeanPropertyRowMapper<>(AuthUser.class);
        try {
            return jdbcTemplate.queryForObject(sql, beanPropertyRowMapper, name);
        } catch (Exception e) {
            return null;
        }
    }

    public List<AuthUser> getAllUsers() {
        String sql = "select * from authUser au where au.role='USER'";
        BeanPropertyRowMapper<AuthUser> beanPropertyRowMapper = new BeanPropertyRowMapper<>(AuthUser.class);
        return jdbcTemplate.query(sql, beanPropertyRowMapper);
    }

    public void blocked(Long user_id) {
        String sql = "update authuser au set isBlocked='t' where au.id=:user_id";
        Map<String, Long> map = Map.of("user_id", user_id);
        namedParameterJdbcTemplate.update(sql, map);
    }public void unblocked(Long user_id) {
        String sql = "update authuser au set isBlocked='f' where au.id=:user_id";
        Map<String, Long> map = Map.of("user_id", user_id);
        namedParameterJdbcTemplate.update(sql, map);
    }
}
