package com.example.gestionchampionnatapi.Repository;

import com.example.gestionchampionnatapi.Models.Championship;
import com.example.gestionchampionnatapi.Models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository  extends JpaRepository<Team, Long> {
    List<Team> findTeamsByChampionshipsId(Long championshipId);
}
