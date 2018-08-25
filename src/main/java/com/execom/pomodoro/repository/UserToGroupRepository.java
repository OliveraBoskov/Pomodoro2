package com.execom.pomodoro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.execom.pomodoro.domain.Team;
import com.execom.pomodoro.domain.User;
import com.execom.pomodoro.domain.UserToGroup;


public interface UserToGroupRepository extends JpaRepository<UserToGroup, Long>{

    List<UserToGroup> findAllByTeam(Team team);
    UserToGroup findOneByTeamAndUser(Team team, User user);
}
