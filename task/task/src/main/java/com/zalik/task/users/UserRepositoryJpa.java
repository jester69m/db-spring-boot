package com.zalik.task.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJpa extends JpaRepository<User, Integer> {

        public User findByUsernameIgnoreCase(String username);
        public User findByEmailIgnoreCase(String email);

        public int countLikesByUserId(int userId);
        public int countPostsByUserId(int userId);
}
