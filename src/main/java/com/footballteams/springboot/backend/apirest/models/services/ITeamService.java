package com.footballteams.springboot.backend.apirest.models.services;

import com.footballteams.springboot.backend.apirest.models.entity.Team;

import java.util.List;

public interface ITeamService {
    public List<Team> findAll();

    public Team findbyId(String name);

    public Team save (Team team);

    public void delete (Team team);

    public void deleteById(String name);
}
