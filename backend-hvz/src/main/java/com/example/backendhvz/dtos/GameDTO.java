package com.example.backendhvz.dtos;

import com.example.backendhvz.enums.GameState;
import lombok.Data;

import java.sql.Date;

@Data
public class GameDTO {
    private long id;
    private String name;
    private GameState state;
    private Date startTime;
    private Date endTime;
    private String nwLat;
    private String nwLng;
    private String seLat;
    private String seLng;
}
