package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.resource.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageGalleryRepository extends JpaRepository<ImageData, Long> {
}
