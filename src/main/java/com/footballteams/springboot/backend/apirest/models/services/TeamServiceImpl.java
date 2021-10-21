package com.footballteams.springboot.backend.apirest.models.services;

import com.footballteams.springboot.backend.apirest.models.dao.ITeamDao;
import com.footballteams.springboot.backend.apirest.models.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamServiceImpl implements ITeamService{

    @Autowired
    private ITeamDao teamDao;

    @Override
    @Transactional(readOnly = true)
    public List<Team> findAll() {
        return (List<Team>) teamDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)

    public Team findbyId(String name) {
        return teamDao.findById(name).orElse(null);
    }

    @Override
    @Transactional
    public Team save(Team team) {
        return teamDao.save(team);
    }

    @Override
    @Transactional
    public void delete(Team team) {
        teamDao.delete(team);
    }

    @Transactional
    public void deleteById(String name) {
        teamDao.deleteById(name);
    }
}
