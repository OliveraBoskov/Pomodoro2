package com.execom.pomodoro.controller;

import java.security.Principal;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.execom.pomodoro.controller.dto.InvitationDto;
import com.execom.pomodoro.controller.dto.LogInDto;
import com.execom.pomodoro.controller.dto.UserDto;
import com.execom.pomodoro.controller.dto.UserInfoDto;
import com.execom.pomodoro.domain.Invitation;
import com.execom.pomodoro.domain.User;
import com.execom.pomodoro.repository.UserRepository;
import com.execom.pomodoro.service.MailService;
import com.execom.pomodoro.service.Mapper;
import com.execom.pomodoro.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    private Mapper mapper;
    
    private UserRepository userRepository;
    
    private MailService mailService;
    
    private static Logger log = Logger.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, Mapper mapper, UserRepository userRepository,
                          MailService mailService) {
        this.userService = userService;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> tempList = userService.getAllUsers();
        List<UserDto> temp2List = mapper.userListToUserDtoList(tempList);
        return new ResponseEntity<>(temp2List, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Long id) {
        User user = userService.getSingleUser(id);
        return new ResponseEntity<>(mapper.userToUserDto(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return new ResponseEntity<>(mapper.userToUserDto(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userService.editUser(id, userDto);
        return new ResponseEntity<>(mapper.userToUserDto(user), HttpStatus.OK);
    }
    
    @GetMapping("/login")
//    public ResponseEntity<LogInDto> login(@RequestBody UserInfoDto userInfoDto){
    public Principal login(Principal principal){
//        log.info("-------------------------------------------------------------------------------");
//        log.info(userInfoDto.getUsername());
//        User user = userService.login(userInfoDto);
//        LogInDto response = new LogInDto();
//        response.setUsername(user.getUsername());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return principal;
    }
    
    @GetMapping("/logout")
  /*  public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, null); 
//        SecurityContextHolder.getContext().setAuthentication(null);
        return "uspesno izlogovan";
    }*/
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          if (auth != null){    
             new SecurityContextLogoutHandler().logout(request, response, auth);
          }
        SecurityContextHolder.getContext().setAuthentication(null);
    }
    
    @PostMapping("/{id}/sendInvitation")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendInvitation(@RequestBody InvitationDto invitationDto, @PathVariable Long id) throws MessagingException{
        
        for(String username: invitationDto.getUserEmails()){
           Invitation invitation = userService.createInvitation(username, id);
           mailService.sendInvitationMail("Olivera", invitation);
        }
    }
}