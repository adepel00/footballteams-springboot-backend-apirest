package com.footballteams.springboot.backend.apirest.models.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LeagueId implements Serializable {
    private String name;
    private Date date;

    public LeagueId(String name, Date date){
        this.name = name;
        this.date = date;
    }

}
