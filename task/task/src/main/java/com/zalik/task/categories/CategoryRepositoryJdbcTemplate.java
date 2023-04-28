package com.zalik.task.categories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryRepositoryJdbcTemplate {

    private JdbcTemplate jdbcTemplate;

    public CategoryRepositoryJdbcTemplate(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM category";
        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }

    public void save(Category category) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        jdbcTemplate.update(sql, category.getName());
    }

    public void update(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, category.getName(), category.getId());
    }

    private class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            return category;
        }
    }
}
