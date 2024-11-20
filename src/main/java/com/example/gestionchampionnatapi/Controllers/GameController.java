package com.example.gestionchampionnatapi.Controllers;

import com.example.gestionchampionnatapi.Models.Day;
import com.example.gestionchampionnatapi.Models.Game;
import com.example.gestionchampionnatapi.Models.Team;
import com.example.gestionchampionnatapi.Repository.DayRepository;
import com.example.gestionchampionnatapi.Repository.GameRepository;
import com.example.gestionchampionnatapi.Repository.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    DayRepository dayRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    GameRepository gameRepository;

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> getGameByGameID(@PathVariable(value="gameId") Long gameId)
    {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if(optionalGame.isPresent())
        {
            Game game = optionalGame.get();
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
    }
    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGame()
    {
        List<Game> games = gameRepository.findAll();
        return new ResponseEntity<>(games, HttpStatus.OK);

    }

    @GetMapping("/game/day/{dayId}")
    public ResponseEntity<List<Game>> getAllGameByDayId(@PathVariable("dayId") Long dayId)
    {
        List<Game> games = gameRepository.findGamesByDayId(dayId);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }
    @PostMapping("/game/{idTeam1}/{idTeam2}/{dayId}")

    public ResponseEntity<Game> createGame(
            @PathVariable(value = "idTeam1") long team1Id,
            @PathVariable(value ="idTeam2") long team2Id,
            @PathVariable(value="dayId") long dayId ,
            @Valid @RequestBody Game game,
            BindingResult bindingResult)
    {
        Optional<Team> optionalTeam1 = teamRepository.findById(team1Id);
        Optional<Team> optionalTeam2 = teamRepository.findById(team2Id);
        Optional<Day> optionalDay = dayRepository.findById(dayId);
        if(optionalTeam1.isPresent() && optionalTeam2.isPresent() && optionalDay.isPresent())
        {
            Team team1 = optionalTeam1.get();
            Team team2 = optionalTeam2.get();
            Day day = optionalDay.get();
            game.setTeam1(team1);
            game.setTeam2(team2);
            game.setTeam1Point(game.getTeam1Point());
            game.setTeam2Point(game.getTeam2Point());

            game.setDay(day);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team or Day not found");
        }


        Game newGame = gameRepository.save(game);
        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @PutMapping("/game/{idGame}")
    public ResponseEntity<Game> updateGame(@PathVariable(value="idGame", required=false) long idGame, @RequestBody Game updatedGameParam)
    {
          Optional<Game> optionalGame = gameRepository.findById(idGame);
          if(optionalGame.isPresent())
          {
              Game existingGame = optionalGame.get();

              existingGame.setTeam1Point(updatedGameParam.getTeam1Point());
              existingGame.setTeam2Point(updatedGameParam.getTeam2Point());
              Game updatedGame = gameRepository.save(existingGame);
              return new ResponseEntity<>(updatedGame, HttpStatus.OK);
          }
          else{
              return new ResponseEntity<>(updatedGameParam, HttpStatus.NO_CONTENT);
          }

    }
    @DeleteMapping("/game/{idGame}")
    public ResponseEntity<Game> deleteGame(@PathVariable(name="idGame" , required = false) Game game)
    {
        gameRepository.delete(game);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

}
