package com.zalik.task.likes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class LikeRepositoryJdbcTemplate {

    private JdbcTemplate jdbcTemplate;

    public LikeRepositoryJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Like> findAll() {
        String sql = "SELECT * FROM like";
        return jdbcTemplate.query(sql, new LikeRowMapper());
    }

    public void save(Like like) {
        String sql = "INSERT INTO like (user_id, post_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, like.getUser().getId(), like.getPost().getId());
    }

    public void update(Like like) {
        String sql = "UPDATE like SET user_id = ?, post_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, like.getUser().getId(), like.getPost().getId(), like.getId());
    }

    private class LikeRowMapper implements RowMapper<Like> {
        @Override
        public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
            Like like = new Like();
            like.setId(rs.getInt("id"));
            return like;
        }
    }
}