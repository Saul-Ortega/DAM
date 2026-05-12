package org.example.actividad_1.controllers;

import org.example.actividad_1.models.Product;
import org.example.actividad_1.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Test
    void testFindAllEndpoint() throws Exception {
        Product product = new Product();
        product.setName("Milk");

        when(service.findAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Milk"));
    }

    @Test
    void testFindByCategoryEndpoint() throws Exception {
        Product product = new Product();
        product.setName("Milk");
        product.setCategory("Food");

        when(service.findByCategory("Food")).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products?category=Food"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Milk"))
                .andExpect(jsonPath("$[0].category").value("Food"));
    }
}
