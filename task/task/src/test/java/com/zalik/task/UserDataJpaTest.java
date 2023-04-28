package com.zalik.task;

import com.zalik.task.likes.Like;
import com.zalik.task.posts.Post;
import com.zalik.task.users.User;
import com.zalik.task.users.UserRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class UserDataJpaTest {

    @Autowired
    private UserRepositoryJpa repos;

    @Test
    public void testCountLikesByUserId() {
        User user = new User("nazar", "nazar@ukma.edu.ua", "password");
        repos.save(user);

        Like like1 = new Like();
        like1.setUser(user);
        repos.getReferenceById(user.getId()).getLikes().add(like1);

        Like like2 = new Like();
        like2.setUser(user);
        repos.getReferenceById(user.getId()).getLikes().add(like2);

        int likeCount = repos.countLikesByUserId(user.getId());

        assertEquals(2, likeCount);
    }

    @Test
    public void testCountPostsByUserId() {
        User user = new User("nazar", "nazar@ukma.edu.ua", "password");
        repos.save(user);

        Post post1 = new Post();
        post1.setUser(user);
        repos.getReferenceById(user.getId()).getPosts().add(post1);

        Post post2 = new Post();
        post2.setUser(user);
        repos.getReferenceById(user.getId()).getPosts().add(post2);

        int likeCount = repos.countLikesByUserId(user.getId());

        assertNotEquals(1, likeCount);
        assertEquals(2, likeCount);
    }
}
