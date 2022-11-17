package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.FaqDto;
import com.manir.springbootecommercerestapi.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/faq")
public class FaqController {
    @Autowired
    private FaqService faqService;

    //add faq api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addFaq")
    public ResponseEntity<FaqDto> addFaq(@RequestBody FaqDto faqDto){
        FaqDto addedFaq = faqService.addFaq(faqDto);
        return new ResponseEntity<>(addedFaq, HttpStatus.CREATED);
    }
    //list all faqs api
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllFaqs")
    public List<FaqDto> listAllFaqs(){
        List<FaqDto> faqs = faqService.listAllFaqs();
        return faqs;
    }

    //get faq by id api
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<FaqDto> getFaqById(@PathVariable Long id){
        FaqDto faq = faqService.getFaqById(id);
        return new ResponseEntity<>(faq, HttpStatus.OK);
    }

    //update faq api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateFaq/{id}")
    public ResponseEntity<FaqDto> updateFaq(@RequestBody FaqDto faqDto, @PathVariable Long id){
        FaqDto updatedFaq = faqService.updateFaq(faqDto, id);
        return new ResponseEntity<>(updatedFaq, HttpStatus.OK);
    }

    //delete faq api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteFaq/{id}")
    public ResponseEntity<String> deleteFaq(@PathVariable Long id){
        faqService.deleteFaq(id);
        return new ResponseEntity<>("Faq with id: "+id+ " is deleted successfully", HttpStatus.OK);
    }
}
