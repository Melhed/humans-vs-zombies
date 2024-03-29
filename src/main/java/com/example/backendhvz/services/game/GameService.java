package com.example.backendhvz.services.game;

import com.example.backendhvz.models.Game;
import com.example.backendhvz.services.CRUDService;

public interface GameService extends CRUDService<Game, Long> {
//    Game addGame(Game game, Long creatingPlayerId);

    Game updateGame(Game game);

    void deleteGameById(Long gameId);

    Game addNewGame(Game game);
}
