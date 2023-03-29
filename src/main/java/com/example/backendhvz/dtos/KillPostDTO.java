package com.example.backendhvz.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KillPostDTO {
    private Long killPosterId;
    private Long killerId;
    private String biteCode;
    private String story;
    private String lat;
    private String lng;
}
