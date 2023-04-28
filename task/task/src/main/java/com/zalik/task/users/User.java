package com.zalik.task.users;

import com.zalik.task.comments.Comment;
import com.zalik.task.likes.Like;
import com.zalik.task.posts.Post;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    @OneToMany(mappedBy = "users")
    private List<Post> posts;
    @OneToMany(mappedBy = "users")
    private List<Comment> comments;
    @OneToMany(mappedBy = "users")
    private List<Like> likes;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
