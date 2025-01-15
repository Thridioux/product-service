package com.erdem.microservices.product.service;

import com.erdem.microservices.product.dto.ProductRequest;
import com.erdem.microservices.product.dto.ProductResponse;
import com.erdem.microservices.product.model.Product;
import com.erdem.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) { // take the productRequest object and save it to the repo
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created successfully", product);
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
        

    }
    public List<ProductResponse> getAllProducts() { // get all products from the repo
        return productRepository.findAll()
            .stream()
            .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
            .toList();
    }

}
