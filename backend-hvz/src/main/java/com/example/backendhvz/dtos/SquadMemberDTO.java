package com.example.backendhvz.dtos;

import lombok.Data;

@Data
public class SquadMemberDTO {
    private Long id;
    private boolean isRankingMember;
    private Long gameId;
    private Long squadId;
    private Long playerId;
}
