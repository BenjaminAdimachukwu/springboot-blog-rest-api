package com.springboot.blog.controller;

import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    AuthService authService;
    // build login endpoint
    @PostMapping(value =  {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login (@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccesstoken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    // build register API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register( @RequestBody RegisterDto registerDto){
       String response =  authService.register(registerDto);
       return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
