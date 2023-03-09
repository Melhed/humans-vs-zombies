package com.example.backendhvz.dtos;

import com.example.backendhvz.enums.GameState;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class GameDTO {
    private long id;
    private String name;
    private GameState state;
    private Timestamp startTime;
    private Timestamp endTime;
    private String nwLat;
    private String nwLng;
    private String seLat;
    private String seLng;
}
