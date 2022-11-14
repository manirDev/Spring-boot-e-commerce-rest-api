package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.SignUpDto;
import com.manir.springbootecommercerestapi.model.Role;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.repository.RoleRepository;
import com.manir.springbootecommercerestapi.repository.UserRepository;
import com.manir.springbootecommercerestapi.service.UserRegisterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SignUpDto registerUser(SignUpDto signUpDto) {

        //convert dto to entity
        User user = mapToEntity(signUpDto);
        //save user to db
        User registeredUser = userRepository.save(user);
        return  mapToDto(registeredUser);
    }

    //map to dto
    private SignUpDto mapToDto(User user){
        SignUpDto signUpDto = modelMapper.map(user, SignUpDto.class);
        return signUpDto;
    }

    //map to entity
    private User mapToEntity(SignUpDto signUpDto){
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUserName(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        //add role to the user
        Role role = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(role));
        return user;
    }
}
