package com.clinkast.qcm.services.api;

public interface MailService {
    
    void sendResetPasswordMail(String recipient, String username, String link);

    void sendSubscriptionMail(String recipient, String username, String link);
}
