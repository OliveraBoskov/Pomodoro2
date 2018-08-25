package com.execom.pomodoro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.execom.pomodoro.controller.dto.InWhichTeamUserFoundDto;
import com.execom.pomodoro.controller.dto.RawInvitationDto;
import com.execom.pomodoro.controller.dto.TeamDto;
import com.execom.pomodoro.domain.Invitation;
import com.execom.pomodoro.domain.Team;
import com.execom.pomodoro.domain.User;
import com.execom.pomodoro.domain.UserToGroup;
import com.execom.pomodoro.repository.InvitationRepository;
import com.execom.pomodoro.repository.TeamRepository;
import com.execom.pomodoro.repository.UserRepository;
import com.execom.pomodoro.repository.UserToGroupRepository;

@Service
public class TeamService {
    
    private TeamRepository teamRepository;
    private InvitationRepository invitationRepository;
    private UserRepository userRepository;
    private UserToGroupRepository userToGroupRepository;
    
    @Autowired
    public TeamService(TeamRepository teamRepository, InvitationRepository invitationRepository,
            UserRepository userRepository, UserToGroupRepository userToGroupRepository) {
        this.teamRepository = teamRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.userToGroupRepository = userToGroupRepository;
    }

    public Team createTeam(TeamDto teamDto){
        Team team = new Team(teamDto.getName(), teamDto.getDescription());
        team.setActive(true);
        return teamRepository.save(team);
    }
    
    public List<Team> getAllTeams(){
        return teamRepository.findAllByActive(true);
    }
    
    public Team getSingleTeam(Long id){
        return teamRepository.getOne(id);
    }
    
    public void deleteTeam(Long id){
        Team team = teamRepository.getOne(id);
        team.setActive(false);
        teamRepository.save(team);
    }
    
    public Team editTeam(Long id, String name, String description){
        Team team = teamRepository.getOne(id);
        team.setName(name);
        team.setDescription(description);
        
        return teamRepository.save(team);
    }
    
    public Invitation getInvitationByActivationKey(String activationLink){
        return invitationRepository.findOneByActivationLink(activationLink);  
    }
    
    public RawInvitationDto extractDataFromInvitation(String key){
        Invitation invitation = getInvitationByActivationKey(key);
        RawInvitationDto rawInvitationDto = new RawInvitationDto();
        rawInvitationDto.setEmail(invitation.getUsername());
        rawInvitationDto.setTeamId(invitation.getTeamId());
        invitationRepository.delete(invitation);
        
        return rawInvitationDto;
    }
    
    public boolean checkIfUserExistsInThatTeamByEmail(String email, Long teamId){
        Team team = teamRepository.findOneById(teamId);
        List<User> users = usersFromTeam(teamId);
        boolean found = false;
        for(User u: users){
            if(u.getUsername().equals(email)){
                found = true;
            }
        }
        return found;
    }
    
    public User addUserToTeam(User user, Team team) {
        UserToGroup userToGroup = new UserToGroup();
        userToGroup.setUser(user);
        userToGroup.setTeam(team);
        userToGroupRepository.save(userToGroup);
        
        user.getUserToGroup().add(userToGroup);
        userRepository.save(user);
        
        team.getUserToGroup().add(userToGroup);
        teamRepository.save(team);
        return user;
    }
    
    public InWhichTeamUserFoundDto checkIfUserExistsInAnyTeam(String email, Long teamId){
        List<Team> teams = teamRepository.findAll();
        List<UserToGroup> userToGroup = userToGroupRepository.findAll();
        InWhichTeamUserFoundDto inWhichTeamUserFoundDto = new InWhichTeamUserFoundDto();
        inWhichTeamUserFoundDto.setFound(false);
        inWhichTeamUserFoundDto.setTeamId(null);
        for(Team t: teams){
            for(UserToGroup u: userToGroup){
                if(u.getUser().getUsername().equals(email)){
                    inWhichTeamUserFoundDto.setFound(true);
                    inWhichTeamUserFoundDto.setTeamId(t.getId());
                }
            }
        }
        return inWhichTeamUserFoundDto;
    }
    
    public List<User> usersFromTeam(Long id){
        Team team = teamRepository.findOneById(id);
        List<UserToGroup> tempList = userToGroupRepository.findAllByTeam(team);
        List<User> users = new ArrayList<>();
        for(UserToGroup u: tempList){
            users.add(u.getUser());
        }
        
        return users;
    }
    
    public void removeUserFromTeam(String email, Long teamId){
        Team team = teamRepository.findOneById(teamId);
        
        List<User> usersFromTeam = usersFromTeam(team.getId());
        for(User u: usersFromTeam){
            if(u.getUsername().equals(email)){
                UserToGroup userToGroup = userToGroupRepository.findOneByTeamAndUser(team, u);
                
                User user = userToGroup.getUser();
                user.getUserToGroup().remove(userToGroup);
                userRepository.save(user);
                
                Team team1 = userToGroup.getTeam();
                team1.getUserToGroup().remove(userToGroup);
                teamRepository.save(team1);
                
                userToGroupRepository.delete(userToGroup);
            }
        }
    }
}
