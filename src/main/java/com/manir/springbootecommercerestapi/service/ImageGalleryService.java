package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.model.FileData;
import com.manir.springbootecommercerestapi.model.ImageData;
import org.springframework.web.multipart.MultipartFile;

public interface ImageGalleryService {
    FileData addImageToProduct(Long productId, MultipartFile file);
}
