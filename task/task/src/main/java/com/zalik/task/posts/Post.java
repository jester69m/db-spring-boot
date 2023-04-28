package com.zalik.task.posts;

import com.zalik.task.categories.Category;
import com.zalik.task.comments.Comment;
import com.zalik.task.likes.Like;
import com.zalik.task.tags.Tag;
import com.zalik.task.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String content;
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "posts")
    private List<Comment> comments;
    @OneToMany(mappedBy = "posts")
    private List<Like> likes;
    @ManyToMany
    private List<Tag> tags;
}