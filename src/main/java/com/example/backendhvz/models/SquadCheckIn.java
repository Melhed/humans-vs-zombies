package com.example.backendhvz.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SquadCheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "squad_check_in_id")
    private Long id;
    @Column(nullable = false)
    private Timestamp timestamp;
    @Column(nullable = false)
    private String lat;
    @Column(nullable = false)
    private String lng;
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "squad_id", referencedColumnName = "squad_id")
    private Squad squad;
    @ManyToOne
    @JoinColumn(name = "squad_member_id", referencedColumnName = "squad_member_id")
    private SquadMember squadMember;
}
