package com.execom.pomodoro.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.execom.pomodoro.domain.Invitation;
import com.execom.pomodoro.domain.User;

@Service
public class MailService {
    
    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;
    
    @Autowired
    public MailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }
    
    public void sendMail(String toWho, String fromWho, String subject, String content) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeHelper = new MimeMessageHelper(message, false, CharEncoding.UTF_8);
//        mimeHelper.setFrom(fromWho);
        mimeHelper.setTo(toWho);
        mimeHelper.setSubject(subject);
        mimeHelper.setText(content, true);
        
        javaMailSender.send(message);
        
    }
    
    public void sendInvitationMail(String fromWhichUser, Invitation invitation){
        Context context = new Context();
        context.setVariable("fromWhichUser", fromWhichUser);
        context.setVariable("invitation", invitation);
        
        String content = templateEngine.process("sendInvitation", context);
        try {
            sendMail(invitation.getUsername(), "olivera", "link to activate invitation", content);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        
//        if(status==true){
//            String content = templateEngine.process("acceptedInvitation", context);
//            sendMail(user.getUsername(), "probnimailexecom123@gmail.com", "accepted invitation", content);
//        }else{
//            String content = templateEngine.process("deniedInvitation", context);
//            sendMail(user.getUsername(), "probnimailexecom123@gmail.com", "denied invitation", content);
//
//        }
        
        
    }
    

}
