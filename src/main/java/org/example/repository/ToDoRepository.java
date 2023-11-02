package org.example.repository;

import org.example.model.AuthUser;
import org.example.model.ToDo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ToDoRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ToDoRepository(JdbcTemplate jdbcTemplate, SimpleJdbcInsert simpleJdbcInsert, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void add(ToDo toDo, Long authUser_id) {
        String sql;
        if (Objects.nonNull(toDo.getId())) {
            sql = "insert into ToDos(id,title,priority,createdAt) values(:id,:title,:priority,:createdAt)";
        } else {
            sql = "insert into ToDos(title,priority,createdAt) values(:title,:priority,:createdAt)";
        }
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(toDo);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        String sql_2 = "insert into authuser_todo (authuser_id,todo_id) values (?,?)";
        jdbcTemplate.update(sql_2, authUser_id, toDo.getId());
    }

    public List<ToDo> getAllToDo(Long user_id) {
        String sql = "select t.* from todos t inner join authuser_todo at on t.id=at.todo_id where at.authuser_id=:id";
        BeanPropertyRowMapper<ToDo> beanPropertyRowMapper = new BeanPropertyRowMapper<>(ToDo.class);
        Map<String, Long> id = Map.of("id", user_id);
        return namedParameterJdbcTemplate.query(sql, id, beanPropertyRowMapper);
    }

    public void delete(Long id) {
        String sql = "delete from ToDos where id=:id";
        Map<String, Long> id1 = Map.of("id", id);
        namedParameterJdbcTemplate.update(sql, id1);
    }

    public void update(ToDo toDo) {
        String sql = "update ToDos set title=:title,priority=:priority,createdAt=:createdAt where id=:id";
        SqlParameterSource source = new BeanPropertySqlParameterSource(toDo);
        namedParameterJdbcTemplate.update(sql, source);
    }

    public ToDo getToDo(Long id) {
        String sql = "select * from ToDos where id =:id";
        Map<String, Long> id1 = Map.of("id", id);
        BeanPropertyRowMapper<ToDo> rowMapper = BeanPropertyRowMapper.newInstance(ToDo.class);
        return namedParameterJdbcTemplate.queryForObject(sql, id1, rowMapper);
    }

    public Long getUserId(Long todo_id) {
        String sql = "select t.* from authuser_todo st inner join authuser t on t.id=st.authuser_id where st.todo_id=:todo_id";
        Map<String, Long> map = Map.of("todo_id", todo_id);
        BeanPropertyRowMapper<AuthUser> beanPropertyRowMapper = new BeanPropertyRowMapper<>(AuthUser.class);
        return Objects.requireNonNull(namedParameterJdbcTemplate.queryForObject(sql, map, beanPropertyRowMapper)).getId();
    }
}
