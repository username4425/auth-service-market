package com.example.authservicemarket.controllers;

import com.example.authservicemarket.dtos.UserCredentials;
import com.example.authservicemarket.entry.MarketUser;
import com.example.authservicemarket.repository.MarketUserRepository;
import com.example.authservicemarket.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthController {
    private PasswordEncoder  encoder;
    private MarketUserRepository repository;
    private JwtUtils jwtUtils;

    public AuthController(PasswordEncoder encoder, MarketUserRepository repository, JwtUtils jwtUtils) {
        this.encoder = encoder;
        this.repository = repository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody UserCredentials userCredentials, HttpServletResponse response){
        Optional<MarketUser> retrievedUser = repository.findById(userCredentials.username());
        if(retrievedUser.isPresent()){
            MarketUser user = retrievedUser.get();
            if(encoder.matches(userCredentials.password(), user.getPassword())){
                String token = jwtUtils.generateToken(user);
                return ResponseEntity.ok().body(token);
            }
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserCredentials userCredentials, @RequestParam(name="as") String as){
        MarketUser user = new MarketUser();
        user.setId(userCredentials.username());
        user.setPassword(encoder.encode(userCredentials.password()));
        log.info("Sign up for {}", as);
        if(as.equals("user"))
            user.setAuthorities(List.of("USER"));
        else if(as.equals("seller"))
            user.setAuthorities(List.of("USER", "SELLER"));
        else
            return ResponseEntity.badRequest().build();
        repository.save(user);
        return ResponseEntity.ok().body("User created");
    }
}
