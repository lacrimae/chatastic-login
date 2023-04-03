package io.chatasticlogin.service.impl;

import io.chatasticlogin.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${email.username}")
    private String from;

    private final JavaMailSender emailSender;

    @Override
    public void sendVerificationMessage(String to, String email) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email);
            helper.setTo(to);
            helper.setSubject("Email Confirmation");
            emailSender.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send verification email");
        }
    }
}
