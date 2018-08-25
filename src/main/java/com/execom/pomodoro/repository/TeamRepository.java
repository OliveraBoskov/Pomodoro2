package com.execom.pomodoro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.execom.pomodoro.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
    
    List<Team> findAllByActive(boolean active);
    Team findOneById(Long id);
}