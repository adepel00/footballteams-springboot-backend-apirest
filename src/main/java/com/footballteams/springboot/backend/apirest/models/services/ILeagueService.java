package com.footballteams.springboot.backend.apirest.models.services;

import com.footballteams.springboot.backend.apirest.models.entity.League;
import com.footballteams.springboot.backend.apirest.models.entity.LeagueId;

import java.util.List;

public interface ILeagueService  {
    public List<League> findAll();

    public League findbyId(LeagueId id);

    public League save (League league);

    public void delete (League league);

    public void deleteById(LeagueId id);
}
