package com.kamneklogs.fullapp.core.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamneklogs.fullapp.core.dto.Message;
import com.kamneklogs.fullapp.core.dto.ProductDTO;
import com.kamneklogs.fullapp.core.entity.Product;
import com.kamneklogs.fullapp.core.service.ProductService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200") // Angular port
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        if (productService.existById(id)) {
            return ResponseEntity.ok(productService.findById(id).get());
        }
        return new ResponseEntity<Message>(new Message("Product not found"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable("name") String name) {
        if (productService.existByName(name)) {
            return ResponseEntity.ok(productService.findByName(name).get());
        }
        return new ResponseEntity<Message>(new Message("Product not found"), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductDTO productdDto) {
        if (StringUtils.isBlank(productdDto.getName())) {
            return new ResponseEntity<Message>(new Message("Without name"), HttpStatus.BAD_REQUEST);
        }
        if (productdDto.getPrice() < 0) {
            return new ResponseEntity<Message>(new Message("The price can't be negative"), HttpStatus.BAD_REQUEST);
        }
        if (productService.existByName(productdDto.getName())) {
            return new ResponseEntity<Message>(new Message("The product is already exist"), HttpStatus.BAD_REQUEST);
        }

        productService.save(ProductDTO.from(productdDto));
        return ResponseEntity.ok(new Message("Product saved"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProductDTO productdDto) {
        if (!productService.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (StringUtils.isBlank(productdDto.getName())) {
            return new ResponseEntity<Message>(new Message("Without name"), HttpStatus.BAD_REQUEST);
        }
        if (productdDto.getPrice() < 0) {
            return new ResponseEntity<Message>(new Message("The price can't be negative"), HttpStatus.BAD_REQUEST);
        }
        if (productService.existByName(productdDto.getName())) {
            return new ResponseEntity<Message>(new Message("The product is already exist"), HttpStatus.BAD_REQUEST);
        }

        Product product = productService.findById(id).get();

        product.setName(productdDto.getName());
        product.setPrice(productdDto.getPrice());

        productService.save(product);
        return ResponseEntity.ok(new Message("Product updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!productService.existById(id)) {
            return ResponseEntity.notFound().build();
        }

        productService.delete(id);
        return ResponseEntity.ok(new Message("Product deleted"));
    }
}