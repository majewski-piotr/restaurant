package com.shop.restaurant.service;

import com.shop.restaurant.model.dto.BoughtPositionWriteModel;
import com.shop.restaurant.model.dto.OrderCommitWriteModel;
import com.shop.restaurant.model.dto.OrderCommitedReadModel;
import com.shop.restaurant.model.BoughtPosition;
import com.shop.restaurant.model.Order;
import com.shop.restaurant.model.dto.OrderReadModel;
import com.shop.restaurant.model.repository.BoughtPositionRepository;
import com.shop.restaurant.model.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
  OrderRepository orderRepository;
  BoughtPositionService boughtPositionService;
  BoughtPositionRepository boughtPositionRepository;
  EmailService emailService;

  public OrderService(OrderRepository orderRepository, BoughtPositionService boughtPositionService,
                      BoughtPositionRepository boughtPositionRepository, EmailService emailService) {
    this.orderRepository = orderRepository;
    this.boughtPositionService = boughtPositionService;
    this.boughtPositionRepository = boughtPositionRepository;
    this.emailService = emailService;
  }

  public Order create(){
    Order created = new Order();
    created.setBoughtPositions(new ArrayList<>());
    return created;
  }

  public OrderReadModel save(Order tosave){
    Order saved = orderRepository.save(tosave);
    return new OrderReadModel(saved);
  }

  private void throwErrorIfCommited(Order order)throws IllegalStateException{
    if(order.isCommited()){
      throw new IllegalStateException("This order is already commited!");
    }
  }
  @Transactional
  public OrderReadModel increaseBoughtPositionInCurrentOrder(BoughtPositionWriteModel addition, int orderId){
    //get current order or create new one
    Order currentOrder = orderRepository.findById(orderId).orElse(create());
    throwErrorIfCommited(currentOrder);

    //check if i have this menu position already in order
    if(currentOrder.getBoughtPositions().stream().anyMatch(
        el -> el.getMenuPosition().getId() == addition.getMenuPositionId())){

      //if so, just increase the quantity instead
      currentOrder.getBoughtPositions().forEach(element ->{
        if(element.getMenuPosition().getId()==addition.getMenuPositionId()){
          element.setQuantity(element.getQuantity()+addition.getQuantity());}
      });
    } else {
      //else, creaete new one and add to order
      var saved = boughtPositionService.save(addition);
      currentOrder.getBoughtPositions().add(boughtPositionRepository.findById(saved.getId()).orElseThrow(
          ()-> new IllegalArgumentException("no such bought position")
      ));
    }
    return new OrderReadModel(currentOrder);
  }

  @Transactional
  public OrderReadModel decreaseBoughtPositionInCurrentOrder(int boughtPositionId, int orderId){
    Order currentOrder = orderRepository.findById(orderId).orElseThrow(
        ()-> new IllegalArgumentException("no such order!"));
    throwErrorIfCommited(currentOrder);

    //i would prefer stream or foreach, but else modifies source and needs to break immediately
    for(int i=0; i<currentOrder.getBoughtPositions().size();i++){
      BoughtPosition element = currentOrder.getBoughtPositions().get(i);
      if(element.getMenuPosition().getId()==boughtPositionId){
        if(element.getQuantity()==1){
          currentOrder.getBoughtPositions().remove(element);
          boughtPositionRepository.delete(element);
        } else {
          element.setQuantity(element.getQuantity()-1);
        }
        break;
      }
    }
    return new OrderReadModel(currentOrder);
  }

  @Transactional
  public OrderCommitedReadModel commitOrder(OrderCommitWriteModel order, int orderId){
    Order currentOrder = orderRepository.findById(orderId).orElseThrow(
        ()-> new IllegalArgumentException("no such order!"));
    throwErrorIfCommited(currentOrder);

    currentOrder.setComment(order.getComment());
    currentOrder.setCommitedTime(LocalDateTime.now());
    currentOrder.setCommited(true);
    OrderCommitedReadModel commited = new OrderCommitedReadModel(currentOrder);
    emailService.sendSimpleMessage("piomaj4@wp.pl","Zamówienie nr"+currentOrder.getId(),commited.toString());
    return commited;
  }

  public List<OrderReadModel> findAll(){
    List<Order> result = orderRepository.findAll();
    return result.stream().map(OrderReadModel::new).collect(Collectors.toList());
  }

  public List<OrderCommitedReadModel> findAllCommited(boolean isCommited){
    List<Order> result = orderRepository.findByCommited(isCommited);
    return result.stream().map(OrderCommitedReadModel::new).collect(Collectors.toList());
  }



}
