package com.footballteams.springboot.backend.apirest.models.entity;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="teams")
public class Team {

    @Id
    @Size(min = 4, max = 24)
    @NotNull
    @NotEmpty
    //@ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "name_id")
    private String name;

    @NotNull
    @NotEmpty
    private String country;

    @Column(name = "rival_team_name")
    private String rivalTeamName;

//    @Column(name = "rival_team")
//    @OneToOne
//    @JoinColumn(name = "name")
//    private Team rivalTeam;

    private String shield;

    @Column(name = "win_dates")
    private ArrayList<Date> winDates;

    public Team(){}

    public Team(String name, String country) {
        this.name = name;
        this.country = country;
        this.winDates = new ArrayList<>();
    }


    public void addWinDate(Date date){
        winDates.add(date);
    }
}