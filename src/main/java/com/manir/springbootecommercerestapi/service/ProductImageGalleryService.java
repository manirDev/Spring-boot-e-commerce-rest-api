package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.model.ImageData;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageGalleryService {

     ImageData addImageToProduct(Long productId, MultipartFile file);

}
