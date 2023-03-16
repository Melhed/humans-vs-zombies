package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.ChatDTO;
import com.example.backendhvz.dtos.GameDTO;
import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.mappers.ChatMapper;
import com.example.backendhvz.mappers.GameMapper;
import com.example.backendhvz.models.Chat;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.services.chat.ChatService;
import com.example.backendhvz.services.game.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/game")
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    public GameController(GameService gameService, GameMapper gameMapper, ChatService chatService, ChatMapper chatMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
    }

    @GetMapping("{gameId}")
    public ResponseEntity<GameDTO> findById(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameMapper.gameToGameDto(gameService.findById(gameId)));
    }

    @GetMapping
    public ResponseEntity<Collection<GameDTO>> findAll() {
        return ResponseEntity.ok(gameMapper.gamesToGameDtos(gameService.findAll()));
    }

    @PostMapping
    public ResponseEntity<GameDTO> add(@RequestBody GameDTO gameDTO) {
        Game game = gameMapper.gameDtoToGame(gameDTO);
        gameService.add(game);
        URI location = URI.create("/" + game.getId());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/add-new-game")
    public ResponseEntity<GameDTO> addNewGame(@RequestBody GameDTO gameDTO) {
        Game game = gameMapper.gameDtoToGame(gameDTO);
        gameService.addNewGame(game);
        URI location = URI.create("/" + game.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{gameId}")
    public ResponseEntity update(@RequestBody GameDTO gameDTO, @PathVariable Long gameId) {
        if (gameId != gameDTO.getId()) return ResponseEntity.badRequest().build();
        Game game = gameMapper.gameDtoToGame(gameDTO);
        return ResponseEntity.ok(gameService.update(game));
    }

    @DeleteMapping("{gameId}")
    public ResponseEntity deleteById(@PathVariable Long gameId) {
        gameService.deleteById(gameId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody GameDTO gameDTO) {
        gameService.delete(gameMapper.gameDtoToGame(gameDTO));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{gameId}/chat")
    public ResponseEntity<Collection<ChatDTO>> getFactionChat(@PathVariable Long gameId, @RequestBody boolean playerIsHuman) {
        return ResponseEntity.ok(chatMapper.chatsToChatDtos(chatService.findAllByGameId(gameId, playerIsHuman)));
    }
    @PostMapping("{gameId}/chat")
    public ResponseEntity addChat(@PathVariable long gameId, @RequestBody ChatDTO chatDTO) {
        chatDTO.setGameId(gameId);
        Chat chat = chatMapper.chatDtoToChat(chatDTO);
        return ResponseEntity.ok(chatMapper.chatToChatDto(chatService.add(chat)));
    }
}
