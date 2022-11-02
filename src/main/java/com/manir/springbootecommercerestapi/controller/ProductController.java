package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.ProductDto;
import com.manir.springbootecommercerestapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //product create api
    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto responseProduct = productService.createProduct(productDto);
        return   new ResponseEntity<>(responseProduct, HttpStatus.CREATED);
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
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable Long productId){
        ProductDto responseProduct = productService.updateProduct(productDto, productId);
        return ResponseEntity.ok(responseProduct);
    }

    //delete product api
    @DeleteMapping("/deleteProduct/{productId}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product with id: "+ productId +" is deleted successfully:)", HttpStatus.OK);
    }

}
