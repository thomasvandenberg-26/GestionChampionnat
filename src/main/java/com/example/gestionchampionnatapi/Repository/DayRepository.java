package com.example.gestionchampionnatapi.Repository;

import com.example.gestionchampionnatapi.Models.Championship;
import com.example.gestionchampionnatapi.Models.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Long > {

  public List<Day> findByChampionship(Championship championship);
}
