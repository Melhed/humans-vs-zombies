package com.example.backendhvz.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class KillDTO {
    private long id;
    private Date timeOfDeath;
    private String story;
    private String lat;
    private String lng;
    private Long game;
    private Long killer;
    private Long victim;
}
