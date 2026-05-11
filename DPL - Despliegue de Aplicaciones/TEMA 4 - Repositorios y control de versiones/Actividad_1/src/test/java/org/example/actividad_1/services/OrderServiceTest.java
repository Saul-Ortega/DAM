package org.example.actividad_1.services;

import org.example.actividad_1.models.Order;
import org.example.actividad_1.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderService service;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setQuantity(20.00);
        order.setDate(LocalDate.of(2026, 1, 1));
        order.setClient("Client 1");
    }

    @Test
    void testInsertOrder() {
        when(repository.save(any(Order.class))).thenReturn(order);
        Order saved = service.insertOrder(new Order());
        assertNotNull(saved);
        assertEquals("Client 1", order.getClient());
        verify(repository, times(1)).save(any(Order.class));
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(order));
        List<Order> list = service.findAll();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        verify(repository, times(1)).findAll();
    }
}
