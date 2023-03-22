package com.example.backendhvz.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class MissionDTO {
    private Long id;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private Integer lat;
    private Integer lng;
    private boolean isHumanVisible;
    private boolean isZombieVisible;
    private Long gameId;
}
