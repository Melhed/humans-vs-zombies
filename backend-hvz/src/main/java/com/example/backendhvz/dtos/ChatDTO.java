package com.example.backendhvz.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class ChatDTO {
    private long id;
    private String message;
    private Date timestamp;
    private boolean isHumanGlobal;
    private boolean isZombieGlobal;
    private long playerId;
    private long gameId;
    private long squadId;
}
