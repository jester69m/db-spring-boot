package com.zalik.task.forums;

import com.zalik.task.categories.Category;
import com.zalik.task.posts.Post;
import com.zalik.task.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "forums")
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "forums", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();
    @OneToMany(mappedBy = "forums", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();
    @OneToMany(mappedBy = "forums", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
}


