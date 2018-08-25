package com.execom.pomodoro.controller.dto;

import com.execom.pomodoro.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String username;
    
    public UserDto(User user) {
        this.username = user.getUsername();
    } 
}
