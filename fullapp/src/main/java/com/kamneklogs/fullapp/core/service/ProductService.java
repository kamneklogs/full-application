package com.kamneklogs.fullapp.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kamneklogs.fullapp.core.entity.Product;
import com.kamneklogs.fullapp.core.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void delete(int id) {
        if(existById(id)){
            productRepository.deleteById(id);
        }
    }

    public boolean existById(int id) {
        return productRepository.existsById(id);
    }

    public boolean existByName(String name){
        return productRepository.existsByName(name);
    }

}
