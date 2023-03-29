package com.example.backendhvz.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class KillDTO {
    private long id;
    private Timestamp timeOfDeath;
    private String story;
    private String lat;
    private String lng;
    private Long game;
    private Long killer;
    private Long victim;
}
