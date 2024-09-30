package com.example.demo.service.impl;

import com.example.demo.entity.AuthenticationResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private SequenceGeneratorService sequenceGenerator;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, SequenceGeneratorService sequenceGenerator, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.sequenceGenerator = sequenceGenerator;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(User user){
        User registeredUser = new User();
        registeredUser.setId(sequenceGenerator.getSequenceNumber(User.SEQUENCE_NAME));
        registeredUser.setUsername(user.getUsername());
        registeredUser.setEmail(user.getEmail());
        registeredUser.setPassword(passwordEncoder.encode(user.getPassword()));

        registeredUser.setRole(user.getRole());

        registeredUser.setImage(user.getImage());

        registeredUser = userRepository.save(registeredUser);

        String token = jwtService.generateToken(registeredUser);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User user){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        User loggedInUser = userRepository.findByUsername(user.getUsername()).get();
        String token = jwtService.generateToken(loggedInUser);

        return new AuthenticationResponse(token);
    }
}
