package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.FaqDto;

import java.util.List;

public interface FaqService {
    FaqDto addFaq(FaqDto faqDto);
    List<FaqDto> listAllFaqs();
    FaqDto getFaqById(Long id);
    FaqDto updateFaq(FaqDto faqDto, Long id);
    void deleteFaq(Long id);
}
