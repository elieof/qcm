package com.clinkast.qcm.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.clinkast.qcm.security.AppUserDetailsService;
import com.clinkast.qcm.services.api.MailService;

@Service
public class MailServiceImpl implements MailService {
    

   private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);
    
    @Autowired
    @Qualifier("resetPasswordMailMessage")
    private SimpleMailMessage resetPasswordMailMessage;
    
    @Autowired
    @Qualifier("subscriptionMailMessage")
    private SimpleMailMessage subscriptionMailMessage;
    
    @Autowired
    private JavaMailSender mailsender;
    


    @Override
    public void sendResetPasswordMail(String recipient, String username, String link) {
	resetPasswordMailMessage.setText(String.format(resetPasswordMailMessage.getText(), username, link));
	resetPasswordMailMessage.setTo(recipient);
	mailsender.send(resetPasswordMailMessage);
    }


    @Override
    public void sendSubscriptionMail(String recipient, String username, String link) {
	subscriptionMailMessage.setText(String.format(subscriptionMailMessage.getText(), username, link));
	subscriptionMailMessage.setTo(recipient);
	mailsender.send(subscriptionMailMessage);
    }

}
