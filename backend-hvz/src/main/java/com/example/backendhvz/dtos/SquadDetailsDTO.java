package com.example.backendhvz.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SquadDetailsDTO {
    private Long id;
    private String name;
    private boolean isHuman;
    private Set<SquadMemberDetailsDTO> squadMembers;
}
