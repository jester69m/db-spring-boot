package com.zalik.task;

import com.zalik.task.users.User;
import com.zalik.task.users.UserRepositoryJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class UserTestWithTestcontainer {

    @Container
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("zalik")
            .withUsername("nazar")
            .withPassword("password");

    @Autowired
    private UserRepositoryJpa repos;

    @BeforeAll
    static void beforeAll(){
        container.start();
    }

    @AfterAll
    static void afterAll(){
        container.stop();
    }

    @AfterEach
    void afterEach(){
        repos.deleteAll();
    }

    @Test
    void findByUsernameIgnoreCaseTest(){
        User user = new User("nazar", "nazar@ukma.edu.ua", "password");
        repos.save(user);

        User founded = repos.findByUsernameIgnoreCase("Nazar");
        assertEquals(user.getUsername(), founded.getUsername());
        assertEquals(user.getEmail(), founded.getEmail());
        assertEquals(user.getPassword(), founded.getPassword());

    }

    @Test
    void findByEmailIgnoreCaseTest(){
        User user = new User("nazar", "nazar@ukma.edu.ua", "password");
        repos.save(user);

        User founded = repos.findByEmailIgnoreCase("NaZar@ukma.edu.ua");
        assertEquals(user.getUsername(), founded.getUsername());
        assertEquals(user.getEmail(), founded.getEmail());
        assertEquals(user.getPassword(), founded.getPassword());

    }
}
