package com.example.backendhvz.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.text.Position;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    private String name;

    private enum state {
        REGISTRATION,
        IN_PROGRESS,
        COMPLETE
    }

    @Column(name = "nw_lat")
    private String nwLat;
    @Column(name = "nw_lng")
    private String nwLng;
    @Column(name = "se_lat")
    private String seLat;
    @Column(name = "se_lng")
    private String seLng;

}
