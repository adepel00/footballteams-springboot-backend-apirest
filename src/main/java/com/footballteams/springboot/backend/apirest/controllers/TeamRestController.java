package com.footballteams.springboot.backend.apirest.controllers;

import com.footballteams.springboot.backend.apirest.models.entity.Team;
import com.footballteams.springboot.backend.apirest.models.services.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.management.ObjectName;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TeamRestController {
    @Autowired
    private ITeamService teamService;

    private Map<String, Object> showMessagesDataAccessException(DataAccessException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        if (e != null) {
            errorResponse.put("Message", "There is an error with the teams database query.");
            errorResponse.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
        }
        return errorResponse;
    }

    private ResponseEntity checkErrorFields(BindingResult result, Map<String, Object> errorResponse){
        List<String> errors = new ArrayList<>();
        for(FieldError err: result.getFieldErrors()){
            errors.add("The field '" + err.getField() + "' " + err.getDefaultMessage());
        }

        errorResponse.put("Errors", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/teams")
    public List<Team> index(){
        return teamService.findAll();
    }

    @GetMapping("/teams/{name}")
    public ResponseEntity<?> show(@PathVariable String name){
        Team team;
        Map<String, Object> errorResponse = new HashMap<>();
        try {
            team = teamService.findbyId(name);
        }catch (DataAccessException e){
            return new ResponseEntity<>(showMessagesDataAccessException(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(team == null){
            errorResponse.put("Message", "There is no team with the name \"".concat(name).concat("\" in our database."));
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @PostMapping("/teams")
    public ResponseEntity<?> create(@Valid @RequestBody Team team, BindingResult result){
        Team newTeam;
        Map<String, Object> errorResponse = new HashMap<>();

        if(result.hasErrors()){
            return checkErrorFields(result, errorResponse);
        }

        try {
            newTeam = teamService.save(team);
        }catch (DataAccessException e){
            return new ResponseEntity<>(showMessagesDataAccessException(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        errorResponse.put("Message", "Team created!");
        errorResponse.put("Team", newTeam);

        return new ResponseEntity<>(errorResponse, HttpStatus.CREATED);
    }

    @PutMapping("teams/{name}")
    public ResponseEntity<?> update(@RequestBody Team team, @PathVariable String name, BindingResult result){
        Team currentTeam = teamService.findbyId(name);
        Map<String, Object> errorResponse = new HashMap<>();

        if(result.hasErrors()){
            return checkErrorFields(result, errorResponse);
        }

        if (currentTeam == null){
            errorResponse.put("Message", "There is no team with the name \"".concat(name).concat("\" in our database."));
            return new ResponseEntity<Map>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Team updatedTeam;
        try {
            //Atribute updates
            currentTeam.setCountry(team.getCountry());
            currentTeam.setRivalTeamName(team.getRivalTeamName());
            currentTeam.setShield(team.getShield());
            currentTeam.setWinDates(team.getWinDates());
            updatedTeam = teamService.save(currentTeam);
        }catch (DataAccessException e){
            return new ResponseEntity<Map>(showMessagesDataAccessException(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(updatedTeam, HttpStatus.CREATED);
    }

    @DeleteMapping("/teams/{name}")
    public ResponseEntity<?> delete(@PathVariable String name){
        Map<String, Object> errorResponse = new HashMap<>();
        try{
            teamService.deleteById(name);
        }catch (DataAccessException e){
            return new ResponseEntity<Map>(showMessagesDataAccessException(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        errorResponse.put("Message", "The client \"".concat(name).concat("\" has been deleted"));
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
