package com.footballteams.springboot.backend.apirest.models.dao;

import com.footballteams.springboot.backend.apirest.models.entity.League;
import com.footballteams.springboot.backend.apirest.models.entity.LeagueId;
import org.springframework.data.repository.CrudRepository;

public interface ILeagueDao extends CrudRepository<League, LeagueId> {}