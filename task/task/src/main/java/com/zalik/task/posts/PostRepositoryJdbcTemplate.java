package com.zalik.task.posts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostRepositoryJdbcTemplate {
    private JdbcTemplate jdbcTemplate;

    public PostRepositoryJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Post> findAll() {
        String sql = "SELECT * FROM post";
        return jdbcTemplate.query(sql, new PostRowMapper());
    }

    public void save(Post post) {
        String sql = "INSERT INTO post (title, content) VALUES (?, ?)";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent());
    }

    public void update(Post post) {
        String sql = "UPDATE post SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getId());
    }

    private class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            return post;
        }
    }
}