package com.zalik.task.tags;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepositoryJpa extends JpaRepository<Tag, Integer> {

    public Tag findByNameIgnoreCase(String tagName);

    public List<Tag> findAllByNameContainingIgnoreCase(String tagName);

}
