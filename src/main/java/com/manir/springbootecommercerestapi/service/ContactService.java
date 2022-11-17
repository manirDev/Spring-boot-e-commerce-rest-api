package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.ContactDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ContactService {
    ContactDto sendMessage(ContactDto contactDto, HttpServletRequest request);
    List<ContactDto> getMessages();
}
