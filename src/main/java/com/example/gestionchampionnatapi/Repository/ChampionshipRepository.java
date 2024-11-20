package com.example.gestionchampionnatapi.Repository;

import com.example.gestionchampionnatapi.Models.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChampionshipRepository  extends JpaRepository<Championship, Long> {


   List<Championship> findChampionshipById(Long championshipId);

   List<Championship> findByNameContaining(String name);
}
