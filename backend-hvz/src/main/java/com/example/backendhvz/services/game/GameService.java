package com.example.backendhvz.services.game;

import com.example.backendhvz.models.Game;
import com.example.backendhvz.services.CRUDService;

public interface GameService extends CRUDService<Game, Long> {

    Game addGame(Game game, Long creatingPlayerId);

    Game updateGame(Game game, Long updatingPlayerId);

    void deleteGameById(Long gameId, Long deletingPlayerId);

    Game addNewGame(Game game);
}
