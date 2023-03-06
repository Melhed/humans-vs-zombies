package com.example.backendhvz.models;

import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.enums.PlayerState;
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
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerState state;

    @Column(name = "is_human", nullable = false)
    private boolean isHuman;
    @Column(name = "is_patient_zero", nullable = false)
    private boolean isPatientZero;
    @Column(name = "bite_code", length = 3)
    private String biteCode;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private Game game;
}
