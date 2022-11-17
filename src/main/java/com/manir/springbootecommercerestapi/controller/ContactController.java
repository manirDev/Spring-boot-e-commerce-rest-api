package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.ContactDto;
import com.manir.springbootecommercerestapi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/sendMessage")
    public ResponseEntity<ContactDto> sendMessage(@RequestBody ContactDto contactDto,
                                                  HttpServletRequest request){
        ContactDto responseMessage =  contactService.sendMessage(contactDto, request);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    //get messages api
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allMessages")
    public List<ContactDto> getAllMessages(){
        return contactService.getMessages();
    }
}
