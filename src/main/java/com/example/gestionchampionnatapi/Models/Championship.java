package com.example.gestionchampionnatapi.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity

public class Championship {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank
    @NotNull(message = "le champ name ne peut pas être null")
    private String name;

    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat()
    @NotNull(message = "le champ start date ne peut pas être null")
    private LocalDate startDate;
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat()
    @NotNull(message = "le champ end date ne peut pas être null")
    private LocalDate endDate;

    @NotNull(message = "le champ wonPoint ne peut pas être null")
    private int wonPoint;

    @NotNull(message = "le champ wonPoint ne peut pas être null")
    private int lostPoint;

    @NotNull(message = "le champ lostPoint ne peut pas être null")
    private int drawPoint;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "championship_team",
            joinColumns = { @JoinColumn(name = "championship_id") },
            inverseJoinColumns = { @JoinColumn(name = "team_id") })

    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy="championship", fetch = FetchType.LAZY)
    private List<Day> days;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getWonPoint() {
        return wonPoint;
    }

    public void setWonPoint(int wonPoint) {
        this.wonPoint = wonPoint;
    }

    public int getLostPoint() {
        return lostPoint;
    }

    public void setLostPoint(int lostPoint) {
        this.lostPoint = lostPoint;
    }

    public int getDrawPoint() {
        return drawPoint;
    }

    public void setDrawPoint(int drawPoint) {
        this.drawPoint = drawPoint;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // Getter et setters pour les autres propriétés
    public void addTeam(Team team)
    {
        this.teams.add(team);
        team.getChampionships().add(this);
    }


    public void addDay(Day day)
    {
        this.days.add(day);

    }


    public Championship(String name, LocalDate startDate, LocalDate endDate, int wonPoint, int lostPoint, int drawPoint) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.wonPoint = wonPoint;
        this.lostPoint = lostPoint;
        this.drawPoint = drawPoint;
    }
    public Championship(){

    }






}
