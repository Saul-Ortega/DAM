package org.example.actividad_1.services;

import org.example.actividad_1.models.Product;
import org.example.actividad_1.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Milk");
        product.setPrice(21.50);
        product.setStock(40.00);
        product.setCategory("Food");
    }

    @Test
    void testInsertProduct() {
        when(repository.save(any(Product.class))).thenReturn(product);
        Product saved = service.insertProduct(new Product());
        assertNotNull(saved);
        assertEquals("Milk", saved.getName());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(product));
        List<Product> list = service.findAll();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        verify(repository, times(1)).findAll();
    }
}
