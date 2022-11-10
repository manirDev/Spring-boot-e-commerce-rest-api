package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.response.CommonResponse;
import com.manir.springbootecommercerestapi.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService{

    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CommonResponse getResponseContent(Page page, List dtoList) {

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setContent(dtoList);
        commonResponse.setPageNo(page.getNumber());
        commonResponse.setPageSize(page.getSize());
        commonResponse.setTotalPages(page.getTotalPages());
        commonResponse.setTotalElements(page.getTotalElements());
        commonResponse.setLast(page.isLast());

        return commonResponse;
    }
}
