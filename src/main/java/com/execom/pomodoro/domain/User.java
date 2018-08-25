package com.execom.pomodoro.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username", unique = true) //username=email za google sign in
    private String username;

    /*@NotNull
    @Column(name = "fullName")
    private String fullName;*/

  /*  @NotNull
    @Column(name = "role")
    private String role;*/

    @NotNull
    private boolean active;
    
    @OneToMany(mappedBy = "user")
    private Set<UserToGroup> userToGroup;
    
    @OneToMany(mappedBy = "user")
    private Set<Pomodoro> pomodoro;

    public User(@NotNull String username) {
        this.username = username;
    }
}
