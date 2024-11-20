package com.example.gestionchampionnatapi.Controllers;

import com.example.gestionchampionnatapi.Models.Championship;
import com.example.gestionchampionnatapi.Models.Team;
import com.example.gestionchampionnatapi.Repository.ChampionshipRepository;
import com.example.gestionchampionnatapi.Repository.TeamRepository;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TeamController {
    @Autowired
    private ChampionshipRepository championshipRepository;
    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams(){
        List<Team> teams = new ArrayList<Team>(teamRepository.findAll());

        if(teams.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(teams, HttpStatus.OK);


    }
    @GetMapping("/championships/{championshipId}/teams")
    public ResponseEntity<List<Team>> getAllTeamsByChampionshipId(@PathVariable(value = "championshipId")long championshipId){

        if(!championshipRepository.existsById(championshipId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found championship with id" + championshipId);
        }
     List<Team> teams = teamRepository.findTeamsByChampionshipsId(championshipId);
     return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @PostMapping("/championship/{championshipId}/team")
    public ResponseEntity<Team> saveTeamInChampionship(@PathVariable(value = "championshipId") long championshipId, @RequestBody Team teamRequest) {
        Team team = championshipRepository.findById(championshipId).map(championship -> {
            long teamId = teamRequest.getId();


            Optional<Team> optionalTeam = teamRepository.findById(teamId);

            if (teamId != 0) {
                if (optionalTeam.isPresent()) {
                    Team _team = optionalTeam.get();
                    championship.addTeam(_team);
                    return teamRepository.save(_team);

                }
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "équipe introuvable");
            }

            championship.addTeam(teamRequest);
            return teamRepository.save(teamRequest);
    }).orElseThrow(() -> new ResourceAccessException("Not found Championship with id = " + championshipId));

        return new ResponseEntity<>(team, HttpStatus.CREATED);
    }
    @GetMapping("/team/{id}")
    public ResponseEntity<Team> getTeamById (@PathVariable("id") long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if(optionalTeam.isPresent())
        {
            Team _team = optionalTeam.get() ;
            return new ResponseEntity<>(_team, HttpStatus.OK);
        }
        throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "equipe introuvable");
    }
    @PostMapping("/team/")
    public ResponseEntity<Team> saveTeam(@Valid @RequestBody Team team, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        }
        else{
            teamRepository.save(new Team(team.getName(),  null));
            return  new ResponseEntity<>(team, HttpStatus.CREATED);
        }
    }

    @PutMapping("/team/{teamId}")
    public ResponseEntity<Team> updateTeam(@PathVariable(name="teamId" ,required = false) Team team, @RequestBody Team teamUpdate, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        }
        else{
            teamUpdate.setId(team.getId());
            teamUpdate.setName(team.getName());
            teamRepository.save(teamUpdate);
            return new ResponseEntity<>(teamUpdate, HttpStatus.CREATED);
        }
    }
    @DeleteMapping("/team/{teamId}")
    public ResponseEntity<Team> deleteTeam(@PathVariable( name="teamId" ,required = false) Team teamDelete)
    {
        teamRepository.delete(teamDelete);
        return new ResponseEntity<>(teamDelete, HttpStatus.OK);

    }

}
