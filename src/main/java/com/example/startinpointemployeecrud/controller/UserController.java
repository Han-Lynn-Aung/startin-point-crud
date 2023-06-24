package com.example.startinpointemployeecrud.controller;

import com.example.startinpointemployeecrud.model.JwtRequest;
import com.example.startinpointemployeecrud.model.JwtResponse;
import com.example.startinpointemployeecrud.model.User;
import com.example.startinpointemployeecrud.service.CustomUserDetailService;
import com.example.startinpointemployeecrud.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

        @Autowired
        private JwtUtil jwtUtil;
        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private CustomUserDetailService userDetailsService;

        @GetMapping("/")
        public String welcome() {
            return "Welcome to the error !!";
        }

        @PostMapping("/login")
        public ResponseEntity<JwtResponse> login(@RequestBody User user) throws Exception {
            try {
                        authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(), user.getPassword()));

            } catch (Exception ex) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            final String token = jwtUtil.generateToken(userDetails);
            System.out.println(token);
            return ResponseEntity.ok( new JwtResponse(token));

        }

}
