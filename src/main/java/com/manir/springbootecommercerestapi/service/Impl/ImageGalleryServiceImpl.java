package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.model.FileData;
import com.manir.springbootecommercerestapi.model.Product;
import com.manir.springbootecommercerestapi.repository.ImageGalleryRepository;
import com.manir.springbootecommercerestapi.repository.ProductRepository;
import com.manir.springbootecommercerestapi.service.ImageGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageGalleryServiceImpl implements ImageGalleryService {

    private static final Path FOLDER_PATH = Paths.get("fileStorage");

    @Autowired
    private ImageGalleryRepository imageGalleryRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public FileData addImageToProduct(Long productId, MultipartFile file) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", productId)
        );

        FileData  createdImage = null;
        String filePath = FOLDER_PATH + "/" + file.getOriginalFilename();

        try {
            createdImage = imageGalleryRepository.save(uploadFile(file,product));
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return createdImage;
    }

    private FileData uploadFile(MultipartFile file, Product product) throws IOException {

        String filePath = FOLDER_PATH + "/" + file.getOriginalFilename();
        FileData fileData = new FileData();
        fileData.setName(file.getOriginalFilename());
        fileData.setType(file.getContentType());
        fileData.setFilePath(filePath);
        fileData.setProduct(product);

        return fileData;
    }
}
