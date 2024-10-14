package com.myapp.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.dto.AppUserData;
import com.myapp.helper.JwtUtil;
import com.myapp.model.Entity.AppUser;
import com.myapp.model.Entity.AuthRequest;
import com.myapp.response.AuthResponse;
import com.myapp.response.BaseResponse;
import com.myapp.service.AppUserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public BaseResponse<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            UserDetails appUser = appUserService.authenticate(authRequest.getUsername(), authRequest.getPassword());
            if (appUser == null) {
                logger.info("[AuthController.Login - error]");
                return new BaseResponse<>("fail", "Invalid credentials", null);
            }

            final String jwt = jwtUtil.generateToken(appUser.getUsername());
            AuthResponse authResponse = new AuthResponse(jwt);
            return new BaseResponse<>("success", "Login successful", authResponse);
        } catch (Exception e) {
            logger.error("Error login", e);
            return new BaseResponse<>("error", "Wrong username or password", null);
        }
    }

    @PostMapping("/register")
    public BaseResponse<AppUser> register(@RequestBody AppUserData userData) {

        AppUser appUser = modelMapper.map(userData, AppUser.class);
        appUserService.registAppUser(appUser);
        return new BaseResponse<>("success", "Product registered successfully", appUser);
    }
}
