package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.LoginDto;
import com.manir.springbootecommercerestapi.dto.SignUpDto;
import com.manir.springbootecommercerestapi.repository.UserRepository;
import com.manir.springbootecommercerestapi.response.JWTAuthResponse;
import com.manir.springbootecommercerestapi.security.JwtTokenProvider;
import com.manir.springbootecommercerestapi.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private JwtTokenProvider tokenProvider;

    //login api
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){

        Authentication authentication = authenticationManager.authenticate(
                                    new UsernamePasswordAuthenticationToken(
                                            loginDto.getUserNameOrEmail(),
                                            loginDto.getPassword()
                                    )
                                );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token from token provider
        String token = tokenProvider.generateToken(authentication);

        return new ResponseEntity<>(new JWTAuthResponse(token), HttpStatus.OK);
    }

    //register api
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        //check for username exists in DB
        if (userRepository.existsByUserName(signUpDto.getUsername())){
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        userRegisterService.registerUser(signUpDto);
        return new ResponseEntity<>("User is successfully registered", HttpStatus.OK);
    }

}
