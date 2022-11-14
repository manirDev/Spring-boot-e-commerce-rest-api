package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.ProductDto;
import com.manir.springbootecommercerestapi.response.CommonResponse;
import com.manir.springbootecommercerestapi.response.ProductResponse;
import com.manir.springbootecommercerestapi.service.ProductService;
import com.manir.springbootecommercerestapi.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //product create api
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/createProduct", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> createProduct(@RequestPart("productDto") ProductDto productDto,
                                                    @RequestPart("file") MultipartFile file){
        ProductDto responseProduct = productService.createProduct(productDto, file);
        return   new ResponseEntity<>(responseProduct, HttpStatus.CREATED);
    }

    //create product with category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{categoryId}/saveProductByCategoryId")
    public ResponseEntity<ProductDto> saveProductByCategoryId(@PathVariable Long categoryId,
                                                              @RequestBody ProductDto productDto){
        ProductDto responseProduct = productService.saveProductByCategoryId(categoryId, productDto);
        return new ResponseEntity<>(responseProduct, HttpStatus.CREATED);
    }



    //get all products api
    @GetMapping("/getAllProduct")
    public CommonResponse getAllProduct(@RequestParam(value = "pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                         @RequestParam(value = "sortBy", defaultValue = Constant.DEFAULT_SORT_BY, required = false) String sortBy,
                                         @RequestParam(value = "sortDir", defaultValue = Constant.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
       CommonResponse responseProductDtoList = productService.getAllProduct(pageNo, pageSize, sortBy, sortDir);
        return responseProductDtoList;
    }

    //get product by id api
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto responseProduct = productService.getProductById(productId);
        return ResponseEntity.ok(responseProduct);
    }

    //update product api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long categoryId,
                                                    @RequestBody ProductDto productDto,
                                                    @PathVariable Long productId){
        ProductDto responseProduct = productService.updateProduct(categoryId, productDto, productId);
        return ResponseEntity.ok(responseProduct);
    }

    //delete product api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct/{productId}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product with id: "+ productId +" is deleted successfully:)", HttpStatus.OK);
    }

}
