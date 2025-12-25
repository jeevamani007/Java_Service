package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired(required = false)
    private JavaMailSender mailSender;
    
    public void sendOTP(String toEmail, String otp) {
        if (mailSender == null) {
            // If email is not configured, print OTP to console for testing
            System.out.println("=== OTP EMAIL (Email not configured) ===");
            System.out.println("To: " + toEmail);
            System.out.println("OTP: " + otp);
            System.out.println("==========================================");
            return;
        }
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP for Registration");
            message.setText("Your OTP for registration is: " + otp + "\n\nThis OTP will expire in 10 minutes.");
            mailSender.send(message);
        } catch (Exception e) {
            // Fallback to console if email fails
            System.out.println("=== OTP EMAIL (Email sending failed) ===");
            System.out.println("To: " + toEmail);
            System.out.println("OTP: " + otp);
            System.out.println("==========================================");
        }
    }
}

