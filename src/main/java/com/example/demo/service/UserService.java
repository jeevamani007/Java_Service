package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean exists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setVerified(true); // Set to true after OTP verification
        userRepository.save(user);
    }

    public boolean login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getPassword().equals(password) && user.isVerified();
        }
        return false;
    }
    
    public boolean isEmailValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
