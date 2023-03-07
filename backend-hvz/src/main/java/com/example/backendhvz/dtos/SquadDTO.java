package com.example.backendhvz.dtos;

import lombok.Data;

@Data
public class SquadDTO {
    private Long id;
    private String name;
    private boolean isHuman;
    private Long gameId;
}
