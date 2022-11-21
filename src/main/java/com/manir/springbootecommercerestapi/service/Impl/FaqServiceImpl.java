package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.FaqDto;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.model.Faq;
import com.manir.springbootecommercerestapi.repository.FaqRepository;
import com.manir.springbootecommercerestapi.service.CommonService;
import com.manir.springbootecommercerestapi.service.FaqService;
import com.manir.springbootecommercerestapi.service.MapperService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FaqServiceImpl implements FaqService {
    @Resource(name = "faqRepository")
    private final FaqRepository faqRepository;
    @Resource(name = "modelMapper")
    private final ModelMapper modelMapper;
    @Resource(name = "mapperService")
    private final MapperService<Faq, FaqDto> mapperService;

    @Override
    public FaqDto addFaq(FaqDto faqDto) {
        Faq faq = mapperService.mapToEntity(faqDto);
        Faq addedFaq = faqRepository.save(faq);

        return mapperService.mapToDto(addedFaq);
    }

    @Override
    public List<FaqDto> listAllFaqs() {
        List<Faq> faqs = faqRepository.findAll();
        return faqs.stream().map(faq -> mapperService.mapToDto(faq)).collect(Collectors.toList());
    }

    @Override
    public FaqDto getFaqById(Long id) {
        Faq faq = faqRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Faq", id));
        return mapToDto(faq);
    }

    @Override
    public FaqDto updateFaq(FaqDto faqDto, Long id) {
        Faq faq = faqRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Faq", id));
        faq.setQuestion(faqDto.getQuestion());
        faq.setAnswer(faqDto.getAnswer());
        Faq updatedFaq = faqRepository.save(faq);
        return mapToDto(updatedFaq);
    }

    @Override
    public void deleteFaq(Long id) {
        Faq faq = faqRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Faq", id));
        faqRepository.delete(faq);
    }

    private FaqDto mapToDto(Faq faq){
        FaqDto faqDto = modelMapper.map(faq, FaqDto.class);
        return faqDto;
    }
    //map to entity
    private Faq mapToEntity(FaqDto faqDto){
        Faq faq = modelMapper.map(faqDto, Faq.class);
        return faq;
    }
}
