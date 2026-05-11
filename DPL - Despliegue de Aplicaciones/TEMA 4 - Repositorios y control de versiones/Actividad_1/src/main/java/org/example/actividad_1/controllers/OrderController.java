package org.example.actividad_1.controllers;

import org.example.actividad_1.models.Order;
import org.example.actividad_1.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping
    public Order insert(@RequestBody Order order) {
        return service.insertOrder(order);
    }

    @GetMapping
    public List<Order> findAll() {
        return service.findAll();
    }
}
