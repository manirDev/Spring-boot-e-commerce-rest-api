package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageGalleryRepository extends JpaRepository<ImageData, Long> {
}
