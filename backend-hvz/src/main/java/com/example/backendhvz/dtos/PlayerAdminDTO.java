package com.example.backendhvz.dtos;

import com.example.backendhvz.enums.PlayerState;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerAdminDTO {
    private Long id;
    private PlayerState state;
    private boolean isHuman;
    private boolean isPatientZero;
    private String biteCode;
    private Long user;
    private Long game;
}
