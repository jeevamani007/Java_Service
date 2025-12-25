package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

@Service
public class OTPService {
    
    private final Map<String, OTPData> otpStore = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private static final long OTP_EXPIRY_TIME = 10 * 60 * 1000; // 10 minutes
    
    public String generateOTP(String email) {
        String otp = String.format("%06d", random.nextInt(1000000));
        long expiryTime = System.currentTimeMillis() + OTP_EXPIRY_TIME;
        otpStore.put(email, new OTPData(otp, expiryTime));
        return otp;
    }
    
    public boolean verifyOTP(String email, String otp) {
        OTPData data = otpStore.get(email);
        if (data == null) {
            return false;
        }
        
        if (System.currentTimeMillis() > data.expiryTime) {
            otpStore.remove(email);
            return false;
        }
        
        if (data.otp.equals(otp)) {
            otpStore.remove(email);
            return true;
        }
        
        return false;
    }
    
    public void removeOTP(String email) {
        otpStore.remove(email);
    }
    
    private static class OTPData {
        String otp;
        long expiryTime;
        
        OTPData(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
}

