package com.example.backendhvz.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SquadMemberDetailsDTO {
    private Long id;
    private boolean isRankingMember;
    private Long gameId;
    private Long squadId;
    private boolean playerIsHuman;
    private Long playerId;
}
