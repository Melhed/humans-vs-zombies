package com.example.backendhvz.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CheckInDTO {
    private Long id;
    private Timestamp timestamp;
    private String lat;
    private String lng;
    private Long gameId;
    private Long squadId;
    private Long squadMemberId;
}
