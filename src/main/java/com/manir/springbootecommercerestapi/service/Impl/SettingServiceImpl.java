package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.SettingDto;
import com.manir.springbootecommercerestapi.exception.EcommerceApiException;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.model.Setting;
import com.manir.springbootecommercerestapi.repository.SettingRepository;
import com.manir.springbootecommercerestapi.service.SettingService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SettingServiceImpl implements SettingService {
    @Resource(name = "settingRepository")
    private final SettingRepository settingRepository;
    @Resource(name = "modelMapper")
    private final ModelMapper modelMapper;

    @Override
    public SettingDto addSettingFirstTime(SettingDto settingDto) {

        Optional<Setting> setting = settingRepository.findAll().stream().findFirst();
        if (!setting.isPresent()){
           Setting firstSetting = mapToEntity(settingDto);
           Setting saveSetting = settingRepository.save(firstSetting);
           return mapToDto(saveSetting);
        }else {
            throw new EcommerceApiException("Setting already exists, please edit it only", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public SettingDto updateSetting(SettingDto settingDto, Long id) {
        Setting setting = settingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Setting", id));
        setting.setTitle(settingDto.getTitle());
        setting.setKeywords(settingDto.getKeywords());
        setting.setDescription(settingDto.getDescription());
        setting.setCompany(settingDto.getCompany());
        setting.setAddress(settingDto.getAddress());
        setting.setPhone(settingDto.getPhone());
        setting.setFax(settingDto.getFax());
        setting.setEmail(settingDto.getEmail());
        setting.setSmtpServer(settingDto.getSmtpServer());
        setting.setSmtpEmail(settingDto.getSmtpEmail());
        setting.setSmtpPassword(settingDto.getSmtpPassword());
        setting.setSmtpPort(settingDto.getSmtpPort());
        setting.setFacebook(settingDto.getFacebook());
        setting.setInstagram(settingDto.getInstagram());
        setting.setTwitter(settingDto.getTwitter());
        setting.setAboutUs(settingDto.getAboutUs());
        setting.setContact(settingDto.getContact());
        setting.setContact(settingDto.getReference());
        //save setting changes
        Setting editedSetting = settingRepository.save(setting);

        return mapToDto(editedSetting);
    }

    //map to dto
    private SettingDto mapToDto(Setting setting){
        SettingDto settingDto = modelMapper.map(setting, SettingDto.class);
        return settingDto;
    }
    //map to entity
    private Setting mapToEntity(SettingDto settingDto){
        Setting setting = modelMapper.map(settingDto, Setting.class);
        return setting;
    }
}
