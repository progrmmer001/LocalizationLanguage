package org.example.repository;

import org.example.model.MyFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FileDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void add(MyFile myFile) {
        String sql = "insert into myfile(title,originalFileName,codeFileName,mediaType,sizefile) values(:title,:originalFileName,:codeFileName,:mediaType,:sizeFile)";
        SqlParameterSource source = new BeanPropertySqlParameterSource(myFile);
        namedParameterJdbcTemplate.update(sql, source);
    }

    public MyFile getFile(String codeFileName) {
        String sql = "select * from myFile mf where mf.codefilename = :codeFileName";
        Map<String, String> map = Map.of("codeFileName", codeFileName);
        BeanPropertyRowMapper<MyFile> beanPropertyRowMapper = new BeanPropertyRowMapper<>(MyFile.class);
        return namedParameterJdbcTemplate.queryForObject(sql, map, beanPropertyRowMapper);
    }

}
