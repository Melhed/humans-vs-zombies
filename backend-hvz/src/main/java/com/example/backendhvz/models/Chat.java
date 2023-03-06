package com.example.backendhvz.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;
    @Column(nullable = false)
    private String message;
    @Column(name = "is_human_global", nullable = false)
    private boolean isHumanGlobal;
    @Column(name = "is_zombie_global", nullable = false)
    private boolean isZombieGlobal;
    @Column(nullable = false)
    private Date timestamp;
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private Player player;
    @ManyToOne
    @JoinColumn(name = "squad_id", referencedColumnName = "squad_id")
    private Squad squad;

}
