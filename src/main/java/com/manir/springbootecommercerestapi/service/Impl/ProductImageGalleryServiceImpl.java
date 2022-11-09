package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.repository.ProductImageGalleryRepository;
import com.manir.springbootecommercerestapi.repository.ProductRepository;
import com.manir.springbootecommercerestapi.model.ImageData;
import com.manir.springbootecommercerestapi.model.Product;
import com.manir.springbootecommercerestapi.service.ProductImageGalleryService;
import com.manir.springbootecommercerestapi.utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class ProductImageGalleryServiceImpl implements ProductImageGalleryService {

    @Resource
    private ProductImageGalleryRepository productImageGalleryRepository;
    @Resource
    private ProductRepository productRepository;

    @Override
    public ImageData addImageToProduct(Long productId, MultipartFile file) {

        Product product = productRepository.findById(productId)
                                            .orElseThrow(
                                                    () -> new ResourceNotFoundException("Product", productId)
                                            );

        ImageData addedImage = null;
        try {
            addedImage = productImageGalleryRepository.save(uploadImage(file,product));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return addedImage;
    }


    private ImageData uploadImage(MultipartFile file, Product product ) throws IOException {
        ImageData imageData = new ImageData();
        imageData.setName(file.getOriginalFilename());
        imageData.setType(file.getContentType());
        imageData.setImageData(ImageUtils.compressImage(file.getBytes()));

        imageData.setProduct(product);
        return imageData;
    }


}
