package com.example.backendhvz.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class CheckInDTO {
    private Long id;
    private Timestamp timestamp;
    private String lat;
    private String lng;
    private Long gameId;
    private Long squadId;
    private Long squadMemberId;
}
