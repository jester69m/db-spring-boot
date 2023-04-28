package com.zalik.task;

import com.zalik.task.users.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityTest {

    private static final String PERSISTENCE_UNIT_NAME = "my_persistence_unit";
    private static EntityManagerFactory emFactory;
    private EntityManager em;

    @BeforeAll
    public static void setup() {
        emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @BeforeEach
    public void init() {
        em = emFactory.createEntityManager();
        em.getTransaction().begin();
    }

    @AfterEach
    public void tearDown() {
        em.getTransaction().rollback();
        em.close();
    }

    @AfterAll
    public static void destroy() {
        emFactory.close();
    }

    @Test
    public void testCreateUser() {
        User user = new User("nazar", "nazar@ukma.edu.ua", "password");
        em.persist(user);

        User persistedUser = em.find(User.class, user.getId());
        assertNotNull(persistedUser);
        assertEquals(user.getUsername(), persistedUser.getUsername());
        assertEquals(user.getEmail(), persistedUser.getEmail());
        assertEquals(user.getPassword(), persistedUser.getPassword());
    }

    @Test
    public void testFindUserByUsername() {
        User user = new User("nazar", "nazar@ukma.edu.ua", "password");
        em.persist(user);

        User foundUser = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", user.getUsername())
                .getSingleResult();

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getPassword(), foundUser.getPassword());
    }

}
