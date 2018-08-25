package com.execom.pomodoro.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RawInvitationDto {
    
    private String email;
    private Long teamId;
    
    public RawInvitationDto(String email, Long teamId) {
        this.email = email;
        this.teamId = teamId;
    }    
}
