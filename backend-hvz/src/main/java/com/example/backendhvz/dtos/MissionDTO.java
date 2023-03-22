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

    public MissionDTO(int i, String test, String testMission, Date startDate, Date endDate, int i1, int i2, boolean b, boolean b1, Long id) {
        this.id = (long) i;
        this.name = test;
        this.description = testMission;
        this.startTime = startDate;
        this.endTime= endDate;
        this.lat = i1;
        this.lng = i2;
        this.isHumanVisible = b;
        this.isZombieVisible = b1;
        this.gameId = id;
    }
}
