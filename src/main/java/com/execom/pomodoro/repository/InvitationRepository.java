package com.execom.pomodoro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.execom.pomodoro.domain.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long>{
    
    Invitation findOneByActivationLink(String activationLink);

}
