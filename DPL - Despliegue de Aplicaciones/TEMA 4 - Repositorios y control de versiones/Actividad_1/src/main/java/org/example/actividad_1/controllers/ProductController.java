package org.example.actividad_1.controllers;

import org.example.actividad_1.models.Product;
import org.example.actividad_1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @PostMapping
    public Product insert(@RequestBody Product product) {
        return service.insertProduct(product);
    }

    @GetMapping
    public List<Product> findAll(@RequestParam(required = false) String category) {
        if (category != null) {
            return service.findByCategory(category);
        }
        return service.findAll();
    }
}
