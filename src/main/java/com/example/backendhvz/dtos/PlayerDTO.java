package com.example.backendhvz.dtos;

import com.example.backendhvz.enums.PlayerState;
import lombok.Data;

@Data
public class PlayerDTO {
    private long id;
    private PlayerState state;
    private boolean isHuman;
    private String biteCode;
    private String user;
    private Long game;
}
