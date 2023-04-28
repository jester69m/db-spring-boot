package com.zalik.task.comments;

import com.zalik.task.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepositoryJpa extends JpaRepository<Comment, Integer> {

    public Comment findByContentContainingIgnoreCase(String content);
    public Comment countAllByPost(Post post);
}
