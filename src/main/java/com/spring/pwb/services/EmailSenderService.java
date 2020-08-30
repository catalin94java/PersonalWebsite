package com.spring.pwb.services;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
  private JavaMailSender javaMailSender;
  
  @Autowired
  public EmailSenderService(JavaMailSender javaMailSender, @Value("${emailpass}") String empass) {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.domain.com");
    mailSender.setPort(587);
    mailSender.setUsername("it@codewillbe.com");
    mailSender.setPassword(empass);
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    props.put("mail.smtp.ssl.trust", "*");
    this.javaMailSender = (JavaMailSender)mailSender;
  }
  
  @Async
  public void sendEmail(SimpleMailMessage email) {
    this.javaMailSender.send(email);
  }
}
