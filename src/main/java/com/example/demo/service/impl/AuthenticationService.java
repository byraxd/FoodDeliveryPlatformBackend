package com.example.demo.service.impl;

import com.example.demo.entity.AuthenticationResponse;
import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private SequenceGeneratorService sequenceGenerator;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private TokenRepository tokenRepository;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, SequenceGeneratorService sequenceGenerator, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepository tokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.sequenceGenerator = sequenceGenerator;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }

    public AuthenticationResponse register(User user){

        User registeredUser = saveUser(user);

        String jwt = jwtService.generateToken(registeredUser);

        saveToken(jwt, registeredUser);

        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse authenticate(User user){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        User loggedInUser = userRepository.findByUsername(user.getUsername()).get();
        String token = jwtService.generateToken(loggedInUser);

        revokeAllTokensByUser( loggedInUser);
        saveToken(token, loggedInUser);

        return new AuthenticationResponse(token);
    }

    private void revokeAllTokensByUser(User user){
        List<Token> validTokensByUser = tokenRepository.findAllByUserIdAndLoggedOutFalse(user.getId());
        if(!validTokensByUser.isEmpty()){
            validTokensByUser.forEach(t ->{
                t.setLoggedOut(true);
            });
        }
        tokenRepository.saveAll(validTokensByUser);
    }

    private User saveUser(User user){
        User registeredUser = new User();
        registeredUser.setId(sequenceGenerator.getSequenceNumber(User.SEQUENCE_NAME));
        registeredUser.setUsername(user.getUsername());
        registeredUser.setEmail(user.getEmail());
        registeredUser.setPassword(passwordEncoder.encode(user.getPassword()));

        registeredUser.setRole(user.getRole());

        registeredUser.setImage(user.getImage());

        registeredUser = userRepository.save(registeredUser);
        return registeredUser;
    }

    private void saveToken(String jwt, User user){
        Token token = new Token();

        token.setId(sequenceGenerator.getSequenceNumber(Token.SEQUENCE_NAME));
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
