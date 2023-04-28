package com.zalik.task.categories;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepositoryJpa extends JpaRepository<Category, Integer> {

    public Category findByNameIgnoreCase(String categoryName);
    public Category findByDescriptionContainingIgnoreCase(String categoryDescription);
}
