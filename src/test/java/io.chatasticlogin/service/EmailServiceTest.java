package io.chatasticlogin.service;

import io.chatasticlogin.service.impl.EmailServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmailServiceTest {

    private static final String TO_ADDRESS = "test@test.com";
    private static final String EMAIL_CONTENT = "test email";

    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private EmailServiceImpl emailService;
    @Captor
    private ArgumentCaptor<MimeMessage> messageCaptor;

    @Test
    void shouldSendVerificationMessageCorrectly() throws MessagingException, IOException {
        MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()));
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendVerificationMessage(TO_ADDRESS, EMAIL_CONTENT);

        verify(mailSender).send(messageCaptor.capture());
        MimeMessage message = messageCaptor.getValue();
        assertEquals(TO_ADDRESS, message.getAllRecipients()[0].toString());
        assertEquals(EMAIL_CONTENT, message.getDataHandler().getContent());
        assertEquals("Email Confirmation", message.getSubject());
    }
}
