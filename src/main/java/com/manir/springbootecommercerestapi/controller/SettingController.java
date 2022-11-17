package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.SettingDto;
import com.manir.springbootecommercerestapi.model.Setting;
import com.manir.springbootecommercerestapi.repository.SettingRepository;
import com.manir.springbootecommercerestapi.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;
    @Autowired
    private SettingRepository settingRepository;

    //post first setting api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createSetting")
    public ResponseEntity<SettingDto> createSetting(@RequestBody SettingDto settingDto){
        SettingDto createdSetting = settingService.addSettingFirstTime(settingDto);
        return new ResponseEntity<>(createdSetting, HttpStatus.CREATED);
    }

    //edit existing setting api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editSetting")
    public ResponseEntity<SettingDto> editSetting(@RequestBody SettingDto settingDto){
        Optional<Setting> existingSetting = settingRepository.findAll().stream().findFirst();
        SettingDto editedSetting = settingService.updateSetting(settingDto, existingSetting.get().getId());

        return ResponseEntity.ok(editedSetting);
    }

}
