package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.SettingDto;

public interface SettingService {
    SettingDto addSettingFirstTime(SettingDto settingDto);
    SettingDto updateSetting(SettingDto settingDto, Long id);
}
