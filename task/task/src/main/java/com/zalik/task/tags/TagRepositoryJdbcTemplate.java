package com.zalik.task.tags;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TagRepositoryJdbcTemplate {
    private JdbcTemplate jdbcTemplate;

    public TagRepositoryJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Tag> findAll() {
        String sql = "SELECT * FROM tag";
        return jdbcTemplate.query(sql, new TagRowMapper());
    }

    public void save(Tag tag) {
        String sql = "INSERT INTO tag (name) VALUES (?)";
        jdbcTemplate.update(sql, tag.getName());
    }

    public void update(Tag tag) {
        String sql = "UPDATE tag SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, tag.getName(), tag.getId());
    }

    private class TagRowMapper implements RowMapper<Tag> {
        @Override
        public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tag tag = new Tag();
            tag.setId(rs.getInt("id"));
            tag.setName(rs.getString("name"));
            return tag;
        }
    }
}
