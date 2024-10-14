package com.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.myapp.model.Entity.AppUser;
import com.myapp.model.Repository.AppUserRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public UserDetails authenticate(String email, String rawPassword) {
        UserDetails userDetails = loadUserByUsername(email);
    
        if (!bCryptPasswordEncoder.matches(rawPassword, userDetails.getPassword())) {
            return null;
        }
    
        return userDetails;
    }

    public AppUser registAppUser(AppUser user) { 
        boolean isUserExist = appUserRepo.findByEmail(user.getEmail()).isPresent();
        if(isUserExist) {
            throw new RuntimeException("User already exist");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return appUserRepo.save(user);
    }
    
}