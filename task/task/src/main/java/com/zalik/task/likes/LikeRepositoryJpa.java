package com.zalik.task.likes;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepositoryJpa extends JpaRepository<Like, Integer> {

    public Like countAllByPostId(Integer postId);
    public Like countAllByUserId(Integer userId);
}
