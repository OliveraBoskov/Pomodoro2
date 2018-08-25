package com.execom.pomodoro.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {
    private String username;
    private String password;

    public UserInfoDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
