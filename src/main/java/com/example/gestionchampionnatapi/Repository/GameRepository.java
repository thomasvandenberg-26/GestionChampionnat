package com.example.gestionchampionnatapi.Repository;

import com.example.gestionchampionnatapi.Models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    public List<Game> findGamesByDayId(long dayId);
}
