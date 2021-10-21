package com.footballteams.springboot.backend.apirest.models.dao;

import com.footballteams.springboot.backend.apirest.models.entity.Team;
import org.springframework.data.repository.CrudRepository;

public interface ITeamDao extends CrudRepository<Team, String> {}