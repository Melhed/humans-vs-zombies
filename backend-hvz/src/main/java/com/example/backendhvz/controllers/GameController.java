package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.ChatDTO;
import com.example.backendhvz.dtos.GameDTO;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.exceptions.RestResponseEntityExceptionHandler;
import com.example.backendhvz.mappers.ChatMapper;
import com.example.backendhvz.mappers.GameMapper;
import com.example.backendhvz.models.Chat;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.services.chat.ChatService;
import com.example.backendhvz.services.game.GameService;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping(path = "api/v1/game")
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;
    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final RestResponseEntityExceptionHandler exceptionHandler;

    public GameController(GameService gameService, GameMapper gameMapper, ChatService chatService, ChatMapper chatMapper, RestResponseEntityExceptionHandler exceptionHandler) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
        this.exceptionHandler = exceptionHandler;
    }

    @GetMapping("{gameId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> findById(@PathVariable Long gameId) {
        try {
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            return ResponseEntity.ok(gameMapper.gameToGameDto(gameService.findById(gameId)));
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch(NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<GameDTO>> findAll() {
        return ResponseEntity.ok(gameMapper.gamesToGameDtos(gameService.findAll()));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody GameDTO gameDTO) {
        try {
//            if(creatingPlayerId == null) throw new BadRequestException("ID of creating player cannot be null.");
            if(gameDTO == null) throw new BadRequestException("Game cannot be null.");

            Game game = gameMapper.gameDtoToGame(gameDTO);
            gameService.add(game);
            URI location = URI.create("/" + game.getId());
            return ResponseEntity.created(location).build();
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch(NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        }
    }

    @PostMapping("/add-new-game")
    @PreAuthorize("hasRole('hvz-admin')")
    public ResponseEntity<GameDTO> addNewGame(@RequestBody GameDTO gameDTO) {
        Game game = gameMapper.gameDtoToGame(gameDTO);
        gameService.addNewGame(game);
        URI location = URI.create("/" + game.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{gameId}")
    @PreAuthorize("hasRole('hvz-admin')")
    public ResponseEntity update(@PathVariable Long gameId, @RequestBody Long updatingPlayerId, @RequestBody GameDTO gameDTO) {
        try {
            if (gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if (gameDTO == null) throw new BadRequestException("Game cannot be null.");
            if (gameId != gameDTO.getId()) throw new BadRequestException("Game IDs don't match.");

            Game game = gameMapper.gameDtoToGame(gameDTO);
            return ResponseEntity.ok(gameService.updateGame(game));
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        }
    }

    @DeleteMapping("{gameId}")
    public ResponseEntity deleteById(@PathVariable Long gameId) {
        try {
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            gameService.deleteGameById(gameId);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody GameDTO gameDTO) {
        try {
            if(gameDTO == null) throw new BadRequestException("Game cannot be null.");
            gameService.delete(gameMapper.gameDtoToGame(gameDTO));
            return ResponseEntity.noContent().build();
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch(NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @GetMapping("{gameId}/chat")
    public ResponseEntity<Object> getFactionChat(@PathVariable Long gameId) {
        try {
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            return ResponseEntity.ok(chatMapper.chatsToChatDtos(chatService.findAllByGameId(gameId)));
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }
    @PostMapping("{gameId}/chat")
    public ResponseEntity addChat(@PathVariable Long gameId, @RequestBody ChatDTO chatDTO) {
        try {
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if(chatDTO == null) throw new BadRequestException("Chat cannot be null.");
            chatDTO.setGameId(gameId);
            Chat chat = chatMapper.chatDtoToChat(chatDTO);
            return ResponseEntity.ok(chatMapper.chatToChatDto(chatService.addGameChat(gameId, chat)));
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }
}
