package com.execom.pomodoro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.execom.pomodoro.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

    List<User> findAllByActive(boolean active);
    User findUserByUsername(String username);
}
