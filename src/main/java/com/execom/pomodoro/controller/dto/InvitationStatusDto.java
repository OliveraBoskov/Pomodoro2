package com.execom.pomodoro.controller.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvitationStatusDto {
    
    private boolean isAccepted; //accept-deny

    public InvitationStatusDto(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
    
    

}
