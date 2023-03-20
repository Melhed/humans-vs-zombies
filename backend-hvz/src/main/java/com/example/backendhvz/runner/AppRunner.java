package com.example.backendhvz.runner;

import com.example.backendhvz.controllers.GameController;
import com.example.backendhvz.controllers.KillController;
import com.example.backendhvz.controllers.PlayerController;
import com.example.backendhvz.controllers.SquadController;
import com.example.backendhvz.dtos.*;
import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.mappers.*;
import com.example.backendhvz.models.*;
import com.example.backendhvz.repositories.ChatRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import com.example.backendhvz.repositories.UserRepository;
import com.example.backendhvz.services.player.PlayerService;

import com.example.backendhvz.services.squad.SquadServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class AppRunner implements ApplicationRunner {
    private final GameController gameController;
    private final GameMapper gameMapper;

    private final UserRepository userRepository;
    private final HvZUserMapper hvZUserMapper;

    private final PlayerController playerController;
    private final PlayerMapper playerMapper;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    private final SquadController squadController;
    private final SquadMapper squadMapper;
    private final SquadServiceImpl squadServiceImpl;

    private final KillController killController;

    public AppRunner(GameController gameController, GameMapper gameMapper, PlayerController playerController, PlayerMapper playerMapper, PlayerService playerService, PlayerRepository playerRepository, UserRepository userRepository, ChatRepository chatRepository, ChatMapper chatMapper, SquadController squadController, SquadMapper squadMapper, HvZUserMapper hvZUserMapper, KillController killController, SquadServiceImpl squadServiceImpl) {
        this.gameController = gameController;
        this.gameMapper = gameMapper;
        this.playerController = playerController;
        this.playerMapper = playerMapper;
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
        this.squadController = squadController;
        this.squadMapper = squadMapper;
        this.hvZUserMapper = hvZUserMapper;
        this.killController = killController;
        this.squadServiceImpl = squadServiceImpl;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        HvZUser[] users = createUsers();
        Game[] games = createGames();
        Player[] players = createPlayers(users, games[2]);
        Squad squad = createSquad(games[2], players[3]);
        joinSquad(games[2], squad, players[4]);
        kill(games[2], players[0], players[3]);
        addChat(games[2], players[3]);
        addSquadChat(games[2], players[3], squad);
        addSquadCheckIn(games[2], squad, players[3]);
        printPlayers(players);
        System.out.println(playerController.findById(games[0].getId(), players[1].getId(), players[1].getId()));
        System.out.println(squadController.findAllSquadChats(games[2].getId(), squad.getId(), players[2].getId()));
    }

    private void addSquadCheckIn(Game game, Squad squad, Player player) {
        squadController.addSquadCheckIn(game.getId(), squad.getId(), new CheckInDTO(null, new Timestamp(System.currentTimeMillis()), "12", "13", game.getId(), squad.getId(), 1L));
    }

    private void addChat(Game game, Player player) {
        gameController.addChat(game.getId(), new ChatDTO(null, "Help", new Timestamp(System.currentTimeMillis()), true, true, player.getId(), game.getId(), null));
    }

    private void addSquadChat(Game game, Player player, Squad squad) {
        squadController.addSquadChat(game.getId(), squad.getId(), new ChatDTO(null, "Here comes the sun", new Timestamp(System.currentTimeMillis()), true, false, player.getId(), game.getId(), squad.getId()));
    }

    private void joinSquad(Game game, Squad squad, Player player) {
        squadController.join(game.getId(), squad.getId(), player.getId());
    }

    private Squad createSquad(Game game, Player player) {
        return squadMapper.squadDtoToSquad((SquadDTO) squadController.add(game.getId(), new SquadPostDTO(player.getId(), "The Beatles")).getBody());
    }

    private void kill(Game game, Player killer, Player victim) {
        killController.add(game.getId(), new KillPostDTO(killer.getId(), killer.getId(), victim.getBiteCode(), "Very sad", "20", "30"));
    }

    private Game[] createGames() {
        Game[] games = new Game[3];
        games[0] = new Game(1L, "Complete game", GameState.COMPLETE, new Timestamp(System.currentTimeMillis()-300), new Timestamp(System.currentTimeMillis()-200), "1", "2", "3", "4");
        games[1] = new Game(2L, "Active game", GameState.IN_PROGRESS, new Timestamp(System.currentTimeMillis()-300), new Timestamp(System.currentTimeMillis()+200), "1", "2", "3", "4");
        games[2] = new Game(3L, "Registration game", GameState.REGISTRATION, new Timestamp(System.currentTimeMillis()+300), new Timestamp(System.currentTimeMillis()+800), "1", "2", "3", "4");

        for (int i = 0; i < games.length; i++)
            gameController.add(gameMapper.gameToGameDto(games[i]));
        return games;
    }
    private HvZUser[] createUsers() {
        HvZUser[] users = new HvZUser[5];
        users[0] = new HvZUser("1", "Billy", "Woods");
        users[1] = new HvZUser("2", "Paul", "McCartney");
        users[2] = new HvZUser("3", "Ringo", "Starr");
        users[3] = new HvZUser("4", "John", "Lennon");
        users[4] = new HvZUser("5", "George", "Harrison");

        for (int i = 0; i < users.length; i++)
            userRepository.save(users[i]);
        return users;
    }

    private Player[] createPlayers(HvZUser[] users, Game game) {
        Player[] players = new Player[5];

        players[0] = new Player(null, PlayerState.ADMINISTRATOR, false, true, "000", users[0], game);
        playerRepository.save(players[0]);

        for (int i = 1; i < users.length; i++) {
            HvZUserDTO user = hvZUserMapper.hvZUserToHvZUserDto(users[i]);
            players[i] = playerService.addNewPlayer(game.getId(),user);
        }

        playerController.update(new PlayerAdminDTO(players[1].getId(), PlayerState.NO_SQUAD, false, true, players[1].getBiteCode(), users[1].getId(), game.getId()), game.getId(), players[0].getId());
        return players;
    }

    private void printPlayers(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            System.out.println("ID: " + players[i].getId());
            System.out.println("Player state: " + players[i].getState());
            System.out.println("is human: " + players[i].isHuman());
            System.out.println("is patient zero: " + players[i].isPatientZero());
            System.out.println("Bite code: " + players[i].getBiteCode());
            System.out.println("User ID: " + players[i].getUser().getId());
            System.out.println("Game ID: " + players[i].getGame().getId());
        }
    }


}

