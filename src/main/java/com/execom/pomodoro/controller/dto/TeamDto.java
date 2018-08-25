package com.execom.pomodoro.controller.dto;

import com.execom.pomodoro.domain.Team;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamDto {
    
    private String name;
    private String description;
    
    public TeamDto(Team team) {
        this.name = team.getName();
        this.description = team.getDescription();
    }  
}
