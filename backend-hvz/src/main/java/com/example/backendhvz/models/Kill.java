package com.example.backendhvz.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Kill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kill_id")
    private Long id;
    @Column(name = "time_of_death")
    private Date timeOfDeath;
    private String story;
    private String lat;
    private String lng;
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "killer_id", referencedColumnName = "player_id")
    private Player killer;
    @ManyToOne
    @JoinColumn(name = "victim_id", referencedColumnName = "player_id")
    private Player victim;

}
