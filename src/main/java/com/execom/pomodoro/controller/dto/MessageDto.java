package com.execom.pomodoro.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDto {
    
    private String message;

    public MessageDto(String message) {
        this.message = message;
    }    
}
