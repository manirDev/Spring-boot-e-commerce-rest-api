package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageGalleryRepository extends JpaRepository<FileData, Long> {
}
