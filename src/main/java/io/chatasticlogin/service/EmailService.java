package io.chatasticlogin.service;

public interface EmailService {

    void sendVerificationMessage(String to, String email);
}
