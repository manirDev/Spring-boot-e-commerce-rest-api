package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.ProductDto;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.repository.CategoryRepository;
import com.manir.springbootecommercerestapi.repository.ProductRepository;
import com.manir.springbootecommercerestapi.model.Category;
import com.manir.springbootecommercerestapi.model.Product;
import com.manir.springbootecommercerestapi.response.CommonResponse;
import com.manir.springbootecommercerestapi.service.CommonService;
import com.manir.springbootecommercerestapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource(name = "modelMapper")
    private ModelMapper modelMapper;
    @Resource(name = "productRepository")
    private ProductRepository productRepository;
    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;

    @Resource
    private CommonService commonService;

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
    public CommonResponse getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Product> products = productRepository.findAll(pageable);

        List<Product> productList = products.getContent();
        //map to dtos
        List<ProductDto> productDtoList = productList.stream()
                                                  .map(product -> mapToDto(product))
                                                  .collect(Collectors.toList());

        CommonResponse productResponse = commonService.getResponseContent(products, productDtoList);
        return productResponse;
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
            logger.error("It is a valid file");
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
