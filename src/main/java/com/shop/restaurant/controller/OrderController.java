package com.shop.restaurant.controller;


import com.shop.restaurant.model.dto.BoughtPositionWriteModel;
import com.shop.restaurant.model.dto.OrderCommitWriteModel;
import com.shop.restaurant.model.dto.OrderCommitedReadModel;
import com.shop.restaurant.model.dto.OrderReadModel;
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

  @PostMapping
  ResponseEntity<OrderReadModel> createNewOrder(){
    OrderReadModel created = orderService.save(orderService.create());
    return ResponseEntity.created(URI.create("/"+created.getId())).body(created);
  }

  @PatchMapping("/{id}/increaseposition")
  ResponseEntity<OrderReadModel> addToOrder(@RequestBody BoughtPositionWriteModel source, @PathVariable int id){
    OrderReadModel patched = orderService.increaseBoughtPositionInCurrentOrder(source, id);
    return ResponseEntity.ok(patched);
  }

  @PatchMapping("/{id}/decreaseposition")
  ResponseEntity<OrderReadModel> decreaseInOrder(@RequestParam int boughtPositionId, @PathVariable int id){
    OrderReadModel patched = orderService.decreaseBoughtPositionInCurrentOrder(boughtPositionId, id);
    return ResponseEntity.ok(patched);
  }

  @PatchMapping("/{id}/commit")
  ResponseEntity<OrderCommitedReadModel> commitOrder(@RequestBody OrderCommitWriteModel order, @PathVariable int id) {
    OrderCommitedReadModel patched = orderService.commitOrder(order,id);
    return ResponseEntity.ok(patched);
  }

  @GetMapping
  ResponseEntity<List<OrderCommitedReadModel>> findAllCommited(@RequestParam boolean isCommited){
    List<OrderCommitedReadModel> commited = orderService.findAllCommited(isCommited);
    return ResponseEntity.ok(commited);
  }
}
