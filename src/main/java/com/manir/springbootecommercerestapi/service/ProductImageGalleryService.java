package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.resource.ImageData;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageGalleryService {

     ImageData addImageToProduct(Long productId, MultipartFile file);

}
