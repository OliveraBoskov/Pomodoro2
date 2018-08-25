package com.execom.pomodoro.controller.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvitationDto {
    
    private List<String> userEmails;

    
    public InvitationDto(List<String> userEmails, Long id) {
        this.userEmails = userEmails;
    }
    
    

}
