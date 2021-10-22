package com.footballteams.springboot.backend.apirest.models.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;

@Data
@Entity
@IdClass(LeagueId.class)
@Table(name="leagues")
public class League {
    @Id
    @NotNull
    @NotEmpty
    private String name;

    @Id
    @NotNull
    @NotEmpty
    @Temporal(TemporalType.DATE)
    private Date date;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "name")
    private ArrayList<Team> teams;

    public League(){}

    public League(String name, Date date){
        this.name = name;
        this.date = date;
        this.teams = new ArrayList<Team>();
    }

    public League(LeagueId id){
        this.name = id.getName();
        this.date = id.getDate();
        this.teams = new ArrayList<Team>();
    }

    public void addTeam(Team team){
        teams.add(team);
    }

    public void removeTeam(Team team){
        teams.remove(team);
    }

    public void removeTeamByName(String teamName){
        teams.removeIf(team -> team.getName().equals(teamName));
    }
}
