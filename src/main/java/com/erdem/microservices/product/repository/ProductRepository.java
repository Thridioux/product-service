package com.erdem.microservices.product.repository;

import com.erdem.microservices.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

//repository interface for product entities
public interface ProductRepository extends MongoRepository<Product, String> {
    


}
