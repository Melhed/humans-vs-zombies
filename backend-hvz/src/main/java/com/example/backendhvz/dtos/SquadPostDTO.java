package com.example.backendhvz.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SquadPostDTO {
    private Long playerId;
    private String squadName;

}
