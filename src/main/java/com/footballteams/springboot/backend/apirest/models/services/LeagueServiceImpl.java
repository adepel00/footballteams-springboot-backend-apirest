package com.footballteams.springboot.backend.apirest.models.services;

import com.footballteams.springboot.backend.apirest.models.dao.ILeagueDao;
import com.footballteams.springboot.backend.apirest.models.entity.League;
import com.footballteams.springboot.backend.apirest.models.entity.LeagueId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class LeagueServiceImpl implements ILeagueService{

    @Autowired
    private ILeagueDao leagueDao;

    @Override
    @Transactional(readOnly = true)
    public List<League> findAll() {
        return (List<League>) leagueDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public League findbyId(LeagueId id) {
        return leagueDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public League save(League league) {
        return leagueDao.save(league);
    }

    @Override
    @Transactional
    public void delete(League league) {
        leagueDao.delete(league);
    }

    @Transactional
    public void deleteById(LeagueId id) {
        leagueDao.deleteById(id);
    }
}
