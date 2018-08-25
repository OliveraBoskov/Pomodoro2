package com.execom.pomodoro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.execom.pomodoro.controller.dto.InWhichTeamUserFoundDto;
import com.execom.pomodoro.controller.dto.InvitationStatusDto;
import com.execom.pomodoro.controller.dto.LeaveTeamDto;
import com.execom.pomodoro.controller.dto.MessageDto;
import com.execom.pomodoro.controller.dto.RawInvitationDto;
import com.execom.pomodoro.controller.dto.TeamDto;
import com.execom.pomodoro.domain.Invitation;
import com.execom.pomodoro.domain.Team;
import com.execom.pomodoro.domain.User;
import com.execom.pomodoro.service.Mapper;
import com.execom.pomodoro.service.TeamService;
import com.execom.pomodoro.service.UserService;

@RestController
@RequestMapping("/api/team")
public class TeamController {
    
    private TeamService teamService;
    private Mapper mapper;
    private UserService userService;
    
    @Autowired
    public TeamController(TeamService teamService, Mapper mapper, UserService userService) {
        this.teamService = teamService;
        this.mapper = mapper;
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto){
        Team team = teamService.createTeam(teamDto);
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.CREATED);
    } 
    
    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams(){
        List<Team> teams = teamService.getAllTeams();
        List<TeamDto> tempList = mapper.teamListToTeamDtoList(teams);
        return new ResponseEntity<>(tempList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getSingleTeam(@PathVariable Long id){
        Team team = teamService.getSingleTeam(id);
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.OK);   
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<MessageDto> deleteTeam(@PathVariable Long id){
        MessageDto messageDto = new MessageDto();
        String response = "";
        teamService.deleteTeam(id);
        response = "Obrisan user";
        messageDto.setMessage(response);
        return new ResponseEntity(messageDto, HttpStatus.OK);
        
    }
    
    @PutMapping()
    public ResponseEntity<TeamDto> editTeam(@PathVariable Long id, @RequestBody TeamDto teamDto){
        Team team = teamService.editTeam(id, teamDto.getName(), teamDto.getDescription());
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.OK);
    }
    
    @GetMapping("/activate")
    public ResponseEntity<MessageDto> activateAccount(@RequestParam(value = "key") String key){
        Invitation invitation = teamService.getInvitationByActivationKey(key);
        MessageDto messageDto = new MessageDto();
        String response = "";
        if (invitation == null) 
            response = "Istekla";
        else
            response = "Odlucite";
        messageDto.setMessage(response);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }
    
    @PostMapping("/activate") //add invited user to team, check if exists somewhere else
    public ResponseEntity<MessageDto> activateAccount(@RequestParam(value = "key") String key, @RequestBody InvitationStatusDto invitationStatusDto){
        RawInvitationDto rawInvitationDto = teamService.extractDataFromInvitation(key); //delete invitation + who is the user
        MessageDto messageDto = new MessageDto();
        String response = "";
        if(invitationStatusDto.isAccepted() == false){
            response = "odbijena pozivnica";
        }else{
            if(!teamService.checkIfUserExistsInThatTeamByEmail(rawInvitationDto.getEmail(), rawInvitationDto.getTeamId())){
               InWhichTeamUserFoundDto inWhichTeamUserFoundDto = teamService.checkIfUserExistsInAnyTeam(rawInvitationDto.getEmail(), rawInvitationDto.getTeamId());
               if(inWhichTeamUserFoundDto.isFound()){
                   teamService.removeUserFromTeam(rawInvitationDto.getEmail(), inWhichTeamUserFoundDto.getTeamId());
               } //izvrsice if, i propasti dole i svakako izvrsiti ovo ispod
               User user = userService.getUserByUsername(rawInvitationDto.getEmail());
               Team team = teamService.getSingleTeam(rawInvitationDto.getTeamId());
               teamService.addUserToTeam(user, team);
               response = "user je dodat";
            }
        }
        messageDto.setMessage(response);
        return new ResponseEntity(messageDto, HttpStatus.OK);
    }
    
    @PutMapping("/{id}/leaveTeam")
    public ResponseEntity<MessageDto> leaveTeam(@PathVariable("id") Long teamId, @RequestBody LeaveTeamDto leaveTeamDto){
        MessageDto messageDto = new MessageDto();
        String response = "";
        
        teamService.removeUserFromTeam(leaveTeamDto.getEmail(), teamId);
        
        response = "User uklonjen iz tima";
        messageDto.setMessage(response);
        return new ResponseEntity(messageDto, HttpStatus.OK);
    }
        
 }
