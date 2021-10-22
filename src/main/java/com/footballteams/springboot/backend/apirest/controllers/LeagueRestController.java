package com.footballteams.springboot.backend.apirest.controllers;

import com.footballteams.springboot.backend.apirest.models.entity.League;
import com.footballteams.springboot.backend.apirest.models.entity.LeagueId;
import com.footballteams.springboot.backend.apirest.models.services.ILeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
public class LeagueRestController {
    @Autowired
    private ILeagueService leagueService;

    private Map<String, Object> showMessagesDataAccessException(DataAccessException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        if (e != null) {
            errorResponse.put("Message", "There is an error with the leagues database query.");
            errorResponse.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return errorResponse;
        }
        return errorResponse;
    }
    
    private ResponseEntity checkErrorFields(BindingResult result, Map<String, Object> errorResponse){
        List<String> errors = new ArrayList<>();
        for(FieldError err: result.getFieldErrors()){
            errors.add("The league field '" + err.getField() + "' " + err.getDefaultMessage());
        }

        errorResponse.put("Errors", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/leagues")
    public List<League> index(){
        return leagueService.findAll();
    }

    @GetMapping("/leagues/{name}/{date}")
    public ResponseEntity<?> show(@PathVariable String name, @PathVariable Date date){
        League league;
        Map<String, Object> errorResponse = new HashMap<>();
        try {
            league = leagueService.findbyId(new LeagueId(name, date));
        }catch (DataAccessException e){
            return new ResponseEntity<Map>(showMessagesDataAccessException(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(league == null){
            errorResponse.put("Message", "There is no league with the name \"".concat(name).concat("\" and year".concat(date.toString()).concat("in our database.")));
            return new ResponseEntity<Map>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(league, HttpStatus.OK);
    }

    @PostMapping("/leagues")
    public ResponseEntity<?> create(@Valid @RequestBody League league, BindingResult result){
        League newLeague;
        Map<String, Object> errorResponse = new HashMap<>();

        if(result.hasErrors()){
            return checkErrorFields(result, errorResponse);
        }

        try {
            newLeague = leagueService.save(league);
        }catch (DataAccessException e){
            return new ResponseEntity<>(showMessagesDataAccessException(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        errorResponse.put("Message", "League created!");
        errorResponse.put("League", newLeague);

        return new ResponseEntity<>(errorResponse, HttpStatus.CREATED);
    }

    @PutMapping("/leagues/{name}/{date}")
    public ResponseEntity<?> update(@RequestBody League league, @PathVariable String name, @PathVariable Date date, BindingResult result){
        League currentLeague = leagueService.findbyId(new LeagueId(name, date));
        Map<String, Object> errorResponse = new HashMap<>();

        if(result.hasErrors()){
            return checkErrorFields(result, errorResponse);
        }

        if (currentLeague == null){
            errorResponse.put("Message", "There is no league with the name \"".concat(name).concat("\" in our database."));
            return new ResponseEntity<Map>(errorResponse, HttpStatus.NOT_FOUND);
        }

        League updatedLeague;
        try {
            //Attribute updates
            currentLeague.setTeams(league.getTeams());
            updatedLeague = leagueService.save(currentLeague);
        }catch (DataAccessException e){
            return new ResponseEntity<Map>(showMessagesDataAccessException(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(updatedLeague, HttpStatus.CREATED);
    }

    @DeleteMapping("/leagues/{name}/{date}")
    public ResponseEntity<?> delete(@PathVariable String name, @PathVariable Date date){
        Map<String, Object> errorResponse = new HashMap<>();
        try{
            leagueService.deleteById(new LeagueId(name, date));
        }catch (DataAccessException e){
            return new ResponseEntity<Map>(showMessagesDataAccessException(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        errorResponse.put("Message", "The client \"".concat(name).concat("\" and year".concat(date.toString()).concat("has been deleted")));
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

}
