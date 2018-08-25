package com.execom.pomodoro.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeaveTeamDto {

    private String email;

    public LeaveTeamDto(String email) {
        this.email = email;
    }  
}
