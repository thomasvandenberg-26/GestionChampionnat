package com.example.gestionchampionnatapi.Controllers;

import com.example.gestionchampionnatapi.Models.Championship;
import com.example.gestionchampionnatapi.Models.Day;
import com.example.gestionchampionnatapi.Models.Team;
import com.example.gestionchampionnatapi.Models.User;
import com.example.gestionchampionnatapi.Repository.ChampionshipRepository;
import com.example.gestionchampionnatapi.Repository.DayRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DayController {

    @Autowired
    ChampionshipRepository championshipRepository;
    @Autowired
    DayRepository dayRepository;
    Championship championship;

    @GetMapping("/day/{dayId}")
    public ResponseEntity<Day> getDayById(@PathVariable(value = "dayId", required = false) long dayId)
    {
        Optional<Day> optionalDay = dayRepository.findById(dayId);
        if(optionalDay.isPresent())
        {
            Day day = optionalDay.get();
            return new ResponseEntity<>(day, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, " journée non trouvée !");
    }
    @GetMapping("/days/{championshipId}")
    public ResponseEntity<List<Day>> getDaysByIdChampionship(@PathVariable(value = "championshipId", required = false) long championshipId)
    {
       Optional<Championship> optionalChampionship = championshipRepository.findById(championshipId);
       if(optionalChampionship.isPresent())
       {
           Championship championship = optionalChampionship.get();
           List<Day> days = dayRepository.findByChampionship(championship);

          return new ResponseEntity<>(days, HttpStatus.OK);
       }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, " journées non trouvées !");

    }
    @PostMapping("/day/{championshipId}")
    public ResponseEntity<Day> saveDay(@PathVariable(value = "championshipId", required = false) long championshipId, @Valid @RequestBody Day day)
    {

        Optional<Championship> optionalChampionship =  championshipRepository.findById(championshipId);
        if(optionalChampionship.isPresent()) {
            Championship championship = optionalChampionship.get();
            day.setChampionship(championship);
            Day savedDay =  dayRepository.save(day);

            return new ResponseEntity<>(savedDay, HttpStatus.CREATED);

        }
        else {
            // Si le championnat correspondant n'est pas trouvé, je renvoie l'erreur
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Championship not found with ID: " + championshipId);
        }

    }
    @GetMapping("/days")
   public ResponseEntity<List<Day>> getDays() {
       List<Day> days = new ArrayList<Day>(dayRepository.findAll());

       if (!days.isEmpty()) {
           return new ResponseEntity<>(days, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(days, HttpStatus.NO_CONTENT);
       }
   }

   @PutMapping("/days/{dayId}")
    public ResponseEntity<Day>  updateDay(@PathVariable(value="dayId") long dayId, @RequestBody Day updateDayParam) {

       Optional<Day> optionalDay = dayRepository.findById(dayId);
       if (optionalDay.isPresent()) {
           Day existingDay = optionalDay.get();

           if (existingDay.getNumber() != null) {
               existingDay.setNumber(updateDayParam.getNumber());

           }
           Day updatedDay = dayRepository.save(existingDay);
           return new ResponseEntity<>(updatedDay, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(updateDayParam, HttpStatus.NO_CONTENT);
       }
   }
        @DeleteMapping("/days/{dayId}")
       public ResponseEntity<Day> deleteMapping(@PathVariable(value="dayId") Day day) {

            if (day == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Journée non trouvé ! ");
            } else {
                dayRepository.delete(day);

            }
            return new ResponseEntity<>(day, HttpStatus.OK);
        }
   }



