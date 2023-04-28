package com.zalik.task.forums;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ForumRepositoryJdbcTemplate {
    private JdbcTemplate jdbcTemplate;

    public ForumRepositoryJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Forum> findAll() {
        String sql = "SELECT * FROM forum";
        return jdbcTemplate.query(sql, new ForumRowMapper());
    }

    public void save(Forum forum) {
        String sql = "INSERT INTO forum (name, description) VALUES (?, ?)";
        jdbcTemplate.update(sql, forum.getName(), forum.getDescription());
    }

    public void update(Forum forum) {
        String sql = "UPDATE forum SET name = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, forum.getName(), forum.getDescription(), forum.getId());
    }

    private class ForumRowMapper implements RowMapper<Forum> {
        @Override
        public Forum mapRow(ResultSet rs, int rowNum) throws SQLException {
            Forum forum = new Forum();
            forum.setId(rs.getInt("id"));
            forum.setName(rs.getString("name"));
            forum.setDescription(rs.getString("description"));
            return forum;
        }
    }
}