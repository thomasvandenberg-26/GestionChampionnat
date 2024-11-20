package com.example.gestionchampionnatapi.Controllers;

import com.example.gestionchampionnatapi.Models.Championship;
import com.example.gestionchampionnatapi.Repository.ChampionshipRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)

public class ChampionshipController {

    @Autowired
    ChampionshipRepository championshipRepository;

    @GetMapping("/championships")
    public ResponseEntity<List<Championship>> getAllChampionsShips(@RequestParam(required = false) String title) {
        List<Championship> championships = new ArrayList<Championship>(championshipRepository.findAll());

        if (title == null)
            championships.addAll(championshipRepository.findAll());
        if (championships.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(championships, HttpStatus.OK);
    }
    @GetMapping("/championships/{id}")
    public ResponseEntity<Championship> getChampionshipById(@PathVariable("id") long id) {

        Optional<Championship> _championship = championshipRepository.findById(id);
        if(_championship.isPresent()) {
            return ResponseEntity.ok(_championship.get());

        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }
    @PostMapping(value = "/championship/")
    public ResponseEntity<Championship> saveChampion(@Valid @RequestBody Championship championship, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        } else {
            ;
            Championship _championship = championshipRepository.save(new Championship(championship.getName(),
                    championship.getStartDate(), championship.getEndDate(),
                    championship.getWonPoint(), championship.getLostPoint(), championship.getDrawPoint()));
            return new ResponseEntity<>(_championship, HttpStatus.CREATED);
        }


    }
   @PutMapping("/championship/{championshipId}")
   public ResponseEntity<Championship> updateChampionship(@PathVariable(name="championshipId", required = false) Championship championship , @RequestBody Championship championshipUpdate, BindingResult bindingResult ) {
       if (championship == null) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "championnat introuvable");
       } else {
           if (bindingResult.hasErrors()) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
           } else {
               championshipUpdate.setId(championship.getId());
               championshipRepository.save(championshipUpdate);
               return new ResponseEntity<>(championshipUpdate, HttpStatus.CREATED);
           }

       }
   }
   @DeleteMapping("/championship/{championshipId}")
    public void deleteChampionShip(@PathVariable(name="championshipId", required = false) Championship championship)
   {
         if(championship == null)
         {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "championnat introuvable");
         }
         else{
             championshipRepository.delete(championship);
         }
   }
}

