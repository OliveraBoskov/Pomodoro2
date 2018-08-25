package com.execom.pomodoro.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.execom.pomodoro.controller.dto.UserDto;
import com.execom.pomodoro.controller.dto.UserInfoDto;
import com.execom.pomodoro.domain.User;
import com.execom.pomodoro.domain.Invitation;
import com.execom.pomodoro.domain.Team;
import com.execom.pomodoro.repository.InvitationRepository;
import com.execom.pomodoro.repository.TeamRepository;
import com.execom.pomodoro.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private TeamRepository teamRepository;
    private InvitationRepository invitationRepository;

    @Autowired
    public UserService(UserRepository userRepository, TeamRepository teamRepository, InvitationRepository invitationRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.invitationRepository = invitationRepository;
    }

    public User createUser(UserDto userDto) {
        User user = new User(userDto.getUsername());
        user.setActive(true);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByActive(true);
    }

    public User getSingleUser(Long id) {
        User user = userRepository.getOne(id);
        return user;
    }

    public void deleteUser(Long id) {
        User user = userRepository.getOne(id);
        user.setActive(false);
        userRepository.save(user);
    }

    public User editUser(Long id, UserDto userDto) {
        User user = userRepository.getOne(id);
        user.setUsername(userDto.getUsername());
        user.isActive();

        return userRepository.save(user);
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    
    public User login(UserInfoDto userInfoDto){        
        User user = userRepository.findUserByUsername(userInfoDto.getUsername());
        if(user == null){
            User user1 = new User();
            user1.setUsername(userInfoDto.getUsername());
            user1.setActive(true);
            userRepository.save(user1);
            return user1;
        }
        return user;
    }
    
    public Invitation createInvitation(String username, Long teamId){
        Invitation invitation = new Invitation();
        
        invitation.setUsername(username);
        invitation.setTeamId(teamId);
        invitation.setActivationLink(UUID.randomUUID().toString());
        
        return invitationRepository.save(invitation);
        
    }
}
