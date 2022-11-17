package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.ContactDto;
import com.manir.springbootecommercerestapi.model.Contact;
import com.manir.springbootecommercerestapi.repository.ContactRepository;
import com.manir.springbootecommercerestapi.service.ContactService;
import com.manir.springbootecommercerestapi.utils.RequestClientIP;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    @Resource(name = "contactRepository")
    private final ContactRepository contactRepository;
    @Resource(name = "modelMapper")
    private final ModelMapper modelMapper;
    @Override
    public ContactDto sendMessage(ContactDto contactDto, HttpServletRequest request) {

        Contact contact = mapToEntity(contactDto);
        String ipAddress = RequestClientIP.getClientIpAddress(request);
        contact.setIpAddress(ipAddress);
        Contact createdMessage = contactRepository.save(contact);

        return mapToDto(createdMessage);
    }

    @Override
    public List<ContactDto> getMessages() {
        List<Contact> messages = contactRepository.findAll();

        return messages.stream()
                                .map(message -> mapToDto(message))
                                .collect(Collectors.toList());
    }

    //map to entity
    private Contact mapToEntity(ContactDto contactDto){
        Contact contact = modelMapper.map(contactDto, Contact.class);
        return contact;
    }
    //map to dto
    private ContactDto mapToDto(Contact contact){
        ContactDto contactDto = modelMapper.map(contact, ContactDto.class);
        return contactDto;
    }
}
