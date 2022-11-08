package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.ProductDto;
import com.manir.springbootecommercerestapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //product create api
    @RequestMapping(value = "/createProduct", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> createProduct(@RequestPart("productDto") ProductDto productDto,
                                                    @RequestPart("file") MultipartFile file){
        ProductDto responseProduct = productService.createProduct(productDto, file);
        return   new ResponseEntity<>(responseProduct, HttpStatus.CREATED);
    }

    //create product with category
    @PostMapping("/{categoryId}/saveProductByCategoryId")
    public ResponseEntity<ProductDto> saveProductByCategoryId(@PathVariable Long categoryId,
                                                              @RequestBody ProductDto productDto){
        ProductDto responseProduct = productService.saveProductByCategoryId(categoryId, productDto);
        return new ResponseEntity<>(responseProduct, HttpStatus.CREATED);
    }



    //get all products api
    @GetMapping("/getAllProduct")
    public List<ProductDto> getAllProduct(){
        List<ProductDto> responseProductDtoList = productService.getAllProduct();
        return responseProductDtoList;
    }

    //get product by id api
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto responseProduct = productService.getProductById(productId);
        return ResponseEntity.ok(responseProduct);
    }

    //update product api
    @PutMapping("/{categoryId}/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long categoryId,
                                                    @RequestBody ProductDto productDto,
                                                    @PathVariable Long productId){
        ProductDto responseProduct = productService.updateProduct(categoryId, productDto, productId);
        return ResponseEntity.ok(responseProduct);
    }

    //delete product api
    @DeleteMapping("/deleteProduct/{productId}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product with id: "+ productId +" is deleted successfully:)", HttpStatus.OK);
    }

}
