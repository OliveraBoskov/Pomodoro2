package com.execom.pomodoro.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InWhichTeamUserFoundDto {
    
    private boolean found;
    private Long teamId;
    
    public InWhichTeamUserFoundDto(boolean found, Long teamId) {
        this.found = found;
        this.teamId = teamId;
    }
}
