package com.example.backendhvz.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SquadMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "squad_member_id")
    private Long id;
    private String rank;
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "squad_id", referencedColumnName = "squad_id")
    private Squad squad;
    @OneToOne
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private Player player;
}
