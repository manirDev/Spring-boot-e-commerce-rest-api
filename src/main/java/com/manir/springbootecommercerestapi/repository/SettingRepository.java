package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
}
