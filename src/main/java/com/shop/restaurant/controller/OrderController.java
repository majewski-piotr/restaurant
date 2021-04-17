package com.shop.restaurant.controller;


import com.shop.restaurant.exception.CategoryNotFoundException;
import com.shop.restaurant.exception.PositionNotFoundException;
import com.shop.restaurant.model.*;
import com.shop.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
  private OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PatchMapping
  ResponseEntity<Order> updateCost(@RequestBody Order order)
      throws PositionNotFoundException, CategoryNotFoundException {
    Order created = orderService.updateCost(order);
    return ResponseEntity.ok(created);
  }

  @PostMapping("/commit")
  ResponseEntity<Order> commitOrder(@RequestBody Order order)
      throws PositionNotFoundException, CategoryNotFoundException {
    Order patched = orderService.saveOrder(order);
    return ResponseEntity.created(URI.create("/history")).body(patched);
  }

  @GetMapping
  ResponseEntity<List<Order>> findAll(){
    List<Order> result = orderService.findAll();
    return ResponseEntity.ok(result);
  }
}
