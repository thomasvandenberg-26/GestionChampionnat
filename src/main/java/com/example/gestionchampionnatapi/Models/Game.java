package com.example.gestionchampionnatapi.Models;

import jakarta.persistence.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    @ManyToOne
    @JoinColumn(name = "team_id1")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_id2")
    private Team team2;


    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;


    private int team1Point;
    private int team2Point;

    public int getTeam1Point() {
        return team1Point;
    }

    public void setTeam1Point(int team1Point) {
        this.team1Point = team1Point;
    }

    public int getTeam2Point() {
        return team2Point;
    }

    public void setTeam2Point(int team2Point) {
        this.team2Point = team2Point;
    }
    public void setDay(Day day) {
        this.day = day;
    }



    public Game(int team1Point, int team2Point) {
        this.team1Point = team1Point;
        this.team2Point = team2Point;
    }

   public Game() {

   }
}
