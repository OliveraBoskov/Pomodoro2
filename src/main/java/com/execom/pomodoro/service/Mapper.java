package com.execom.pomodoro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.execom.pomodoro.controller.dto.TeamDto;
import com.execom.pomodoro.controller.dto.UserDto;
import com.execom.pomodoro.domain.Team;
import com.execom.pomodoro.domain.User;

@Service
public class Mapper {
    
    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        return userDto;
    }
    
    public List<UserDto> userListToUserDtoList(List<User> userList) {
        List<UserDto> tempList = new ArrayList<>();
        for(User user: userList) {
         tempList.add(userToUserDto(user));
        }
        return tempList;
    }
    
    public TeamDto teamToTeamDto(Team team){
        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        teamDto.setDescription(team.getDescription());
        return teamDto;
    }
    
    public List<TeamDto> teamListToTeamDtoList(List<Team> teamList){
        List<TeamDto> tempList = new ArrayList<>();
        for(Team team: teamList){
            tempList.add(teamToTeamDto(team));
        }
        return tempList;
    }
}
