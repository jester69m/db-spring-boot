package com.zalik.task.comments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.zalik.task.posts.Post;
import com.zalik.task.users.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CommentRepositoryJdbcTemplate {
    private JdbcTemplate jdbcTemplate;

    public CommentRepositoryJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Comment> findAll() {
        String sql = "SELECT * FROM comment";
        return jdbcTemplate.query(sql, new CommentRowMapper());
    }

    public void save(Comment comment) {
        String sql = "INSERT INTO comment (content, user_id, post_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, comment.getContent(), comment.getUser().getId(), comment.getPost().getId());
    }

    public void update(Comment comment) {
        String sql = "UPDATE comment SET content = ?, user_id = ?, post_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, comment.getContent(), comment.getUser().getId(), comment.getPost().getId(), comment.getId());
    }

    private class CommentRowMapper implements RowMapper<Comment> {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setContent(rs.getString("content"));
            User user = new User();
            user.setId(rs.getInt("user_id"));
            comment.setUser(user);
            Post post = new Post();
            post.setId(rs.getInt("post_id"));
            comment.setPost(post);
            return comment;
        }
    }
}





