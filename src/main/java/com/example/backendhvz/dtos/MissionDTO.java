package com.example.backendhvz.dtos;

import lombok.Data;

import java.sql.Date;

@Data
public class MissionDTO {
    private long missionID;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private String lat;
    private String lng;
    private boolean isHumanVisible;
    private boolean isZombieVisible;
    private Long gameId;

    public MissionDTO(long i, String test, String testMission, Date startDate, Date endDate, String i1, String i2, boolean b, boolean b1, Long id) {
        this.missionID = i;
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
