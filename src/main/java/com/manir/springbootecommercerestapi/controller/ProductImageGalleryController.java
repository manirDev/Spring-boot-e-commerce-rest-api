package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.service.ImageGalleryService;
import com.manir.springbootecommercerestapi.service.ProductImageGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "api/v1/products")
public class ProductImageGalleryController {

    @Autowired
    private ProductImageGalleryService productImageGalleryService;
    @Autowired
    private ImageGalleryService imageGalleryService;


    //add image to product api
    @PostMapping("/{productId}/addImage")
    public ResponseEntity<String> addImageToProduct(@PathVariable Long productId,
                                                    @RequestParam("file")MultipartFile file){
        productImageGalleryService.addImageToProduct(productId, file);
        return new ResponseEntity<>("Image  successfully added to product with id : " + productId, HttpStatus.CREATED);
    }

    //add image to product using file system
    @PostMapping("/{productId}/addImage")
    public ResponseEntity<String> addImageToProductFileSystem(@PathVariable Long productId,
                                                    @RequestParam("file")MultipartFile file){
        imageGalleryService.addImageToProduct(productId, file);
        return new ResponseEntity<>("Image  successfully added to product with id : " + productId, HttpStatus.CREATED);
    }
}
