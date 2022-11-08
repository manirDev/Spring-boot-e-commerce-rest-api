package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.ProductDto;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.repository.CategoryRepository;
import com.manir.springbootecommercerestapi.repository.ProductRepository;
import com.manir.springbootecommercerestapi.resource.Category;
import com.manir.springbootecommercerestapi.resource.ImageData;
import com.manir.springbootecommercerestapi.resource.Product;
import com.manir.springbootecommercerestapi.service.ProductService;
import com.manir.springbootecommercerestapi.utils.ImageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource(name = "modelMapper")
    private ModelMapper modelMapper;
    @Resource(name = "productRepository")
    private ProductRepository productRepository;
    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto, MultipartFile file) {
        //map to entity
        productDto.setImage(uploadProductImage(file));
        Product product = mapToEntity(productDto);
        Product createdProduct = productRepository.save(product);
        //map to dto
        ProductDto responseProduct = mapToDto(createdProduct);
        return responseProduct;
    }

    @Override
    public List<ProductDto> getAllProduct() {

        List<Product> products = productRepository.findAll();
        //map to dtos
        List<ProductDto> productDtoList = products.stream()
                                                  .map(product -> mapToDto(product))
                                                  .collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public ProductDto getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                                           .orElseThrow(
                                                ()->new ResourceNotFoundException("Product", productId)
                                            );
        //map to dto
        ProductDto responseProduct = mapToDto(product);

        return responseProduct;
    }

    @Override
    public ProductDto updateProduct(Long categoryId, ProductDto productDto, Long productId) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(
                                                ()->new ResourceNotFoundException("Product", productId)
                                           );
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(
                                                    ()->new ResourceNotFoundException("Category", categoryId)
                                              );
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setKeywords(productDto.getKeywords());
        product.setPrice(productDto.getPrice());
        product.setDetail(productDto.getDetail());
        product.setQuantity(productDto.getQuantity());
        product.setStatus(productDto.getStatus());
        product.setCategory(category);
        Product updatedProduct = productRepository.save(product);
        //map to dto
        ProductDto responseProduct = mapToDto(updatedProduct);
        return responseProduct;
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(
                                                ()->new ResourceNotFoundException("Product", productId)
                                           );
        productRepository.delete(product);
    }

    @Override
    public ProductDto saveProductByCategoryId(Long categoryId, ProductDto productDto) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow( () -> new ResourceNotFoundException("Category", categoryId));
        Product product = mapToEntity(productDto);

        product.setCategory(category);
        Product createdProduct = productRepository.save(product);
        ProductDto responseProduct = mapToDto(createdProduct);

        return responseProduct;
    }

    //upload image
    private String uploadProductImage(MultipartFile file){
        ProductDto productDto = new ProductDto();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            System.out.println("Not a valid file");
        }
        try {
            productDto.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productDto.getImage();
    }


    //map to dto
    private ProductDto mapToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return  productDto;
    }
    //map to entity
    private Product mapToEntity(ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        return product;
    }
}
