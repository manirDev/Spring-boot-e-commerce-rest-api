package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.SignUpDto;

public interface UserRegisterService {
    SignUpDto registerUser(SignUpDto signUpDto);
}
