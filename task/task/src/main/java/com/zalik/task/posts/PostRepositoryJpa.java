package com.zalik.task.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositoryJpa extends JpaRepository<Post, Integer> {

    public Post findByTitleIgnoreCase(String postTitle);
    public Post findByContentContainingIgnoreCase(String postContent);
}
