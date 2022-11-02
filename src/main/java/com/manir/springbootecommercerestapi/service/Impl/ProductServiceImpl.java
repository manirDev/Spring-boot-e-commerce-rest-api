package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.ProductDto;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.repository.ProductRepository;
import com.manir.springbootecommercerestapi.resource.Product;
import com.manir.springbootecommercerestapi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource(name = "modelMapper")
    private ModelMapper modelMapper;
    @Resource(name = "productRepository")
    private ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        //map to entity
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
    public ProductDto updateProduct(ProductDto productDto, Long productId) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(
                                                ()->new ResourceNotFoundException("Product", productId)
                                           );
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setKeywords(productDto.getKeywords());
        product.setPrice(productDto.getPrice());
        product.setDetail(productDto.getDetail());
        product.setQuantity(productDto.getQuantity());
        product.setStatus(productDto.getStatus());

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
