package com.example.backendhvz.runner;

import com.example.backendhvz.controllers.GameController;
import com.example.backendhvz.controllers.KillController;
import com.example.backendhvz.controllers.PlayerController;
import com.example.backendhvz.controllers.SquadController;
import com.example.backendhvz.dtos.ChatDTO;
import com.example.backendhvz.dtos.GameDTO;
import com.example.backendhvz.dtos.KillPostDTO;
import com.example.backendhvz.dtos.SquadPostDTO;
import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.mappers.*;
import com.example.backendhvz.models.*;
import com.example.backendhvz.repositories.ChatRepository;
import com.example.backendhvz.repositories.UserRepository;
import com.example.backendhvz.services.player.PlayerService;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Component
public class AppRunner implements ApplicationRunner {
    private final GameController gameController;
    private final GameMapper gameMapper;
    private final PlayerController playerController;
    private final PlayerMapper playerMapper;
    private final PlayerService playerService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final SquadController squadController;
    private final SquadMapper squadMapper;
    private final HvZUserMapper hvZUserMapper;
    private final KillController killController;

    public AppRunner(GameController gameController, GameMapper gameMapper, PlayerController playerController, PlayerMapper playerMapper, PlayerService playerService, UserRepository userRepository, ChatRepository chatRepository, ChatMapper chatMapper, SquadController squadController, SquadMapper squadMapper, HvZUserMapper hvZUserMapper, KillController killController) {
        this.gameController = gameController;
        this.gameMapper = gameMapper;
        this.playerController = playerController;
        this.playerMapper = playerMapper;
        this.playerService = playerService;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
        this.squadController = squadController;
        this.squadMapper = squadMapper;
        this.hvZUserMapper = hvZUserMapper;
        this.killController = killController;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Game game = new Game(1L, "GAME1", GameState.COMPLETE, new Date(1,1,1), new Date(2,2,2), "1", "2", "3", "4");
        GameDTO gameDTO = gameMapper.gameToGameDto(game);
        gameController.add(gameDTO);
        HvZUser user = new HvZUser(null, "Billy", "Woods");
        HvZUser user1 = new HvZUser(null, "Filly", "Foods");
        userRepository.save(user);
        userRepository.save(user1);
        Player killer = playerService.addNewPlayer(1L, hvZUserMapper.hvZUserToHvZUserDto(user));
        Player victim = playerService.addNewPlayer(1L, hvZUserMapper.hvZUserToHvZUserDto(user1));
        killController.add(1L, new KillPostDTO(killer.getId(), victim.getBiteCode(), "Very sad", "20", "30"));
        squadController.add(1L, new SquadPostDTO(1L, "Makaronerna"));
        squadController.join(1L, 1L, 2L);
        gameController.addChat(1L, new ChatDTO(null, "hello", new Timestamp(System.currentTimeMillis()), false, true, 1L, 1L, 1L));
        System.out.println(squadController.findAllSquadChats(1L, 1L, 2L));
        //        Player player = new Player(1L, PlayerState.ADMINISTRATOR, true, false, "HOT", user ,game);
//        PlayerAdminDTO playerAdminDTO = playerMapper.playerToPlayerAdminDto(player);
//        playerController.add(1L, playerAdminDTO);
//        Player newPlayer = new Player(2L, PlayerState.NO_SQUAD, false, false, "HT8", user ,game);
//
//        playerController.update(playerMapper.playerToPlayerAdminDto(newPlayer),1L,1L);
//        Squad squad = new Squad(1L, "squad", true, game);
//        squadController.add(1L, squadMapper.squadToSquadDto(squad));
//        Chat chat = new Chat(1L, "HELO", false, true, new Date(2020,02,15), null, player, squad);
//
//
//        gameController.addChat(1L, chatMapper.chatToChatDto(chat));
//        System.out.println(gameController.getFactionChat(1L, true));
    }
}

