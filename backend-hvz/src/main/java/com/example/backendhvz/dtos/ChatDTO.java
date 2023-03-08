package com.example.backendhvz.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private String message;
    private Timestamp timestamp;
    private boolean isHumanGlobal;
    private boolean isZombieGlobal;
    private Long playerId;
    private Long gameId;
    private Long squadId;
}
