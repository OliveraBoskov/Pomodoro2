package com.execom.pomodoro.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogInDto {

    private String username;
    
    public LogInDto(String username, String userRole) {
        this.username = username;
    }
}
