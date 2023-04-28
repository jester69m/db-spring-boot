package com.zalik.task.forums;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ForumRepositoryJpa extends JpaRepository<Forum, Integer> {

    public Forum findByNameIgnoreCase(String forumName);
    public Forum findByDescriptionContainingIgnoreCase(String forumDescription);
}
