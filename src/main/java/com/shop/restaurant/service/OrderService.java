package com.shop.restaurant.service;

import com.shop.restaurant.model.*;
import com.shop.restaurant.model.dto.*;
import com.shop.restaurant.persistence.BoughtPosition;
import com.shop.restaurant.persistence.Order;
import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class OrderService {
  EmailService emailService;
  MenuPositionService menuPositionService;
  ExecutorService executor = Executors.newFixedThreadPool(10);

  @PersistenceContext
  EntityManager em;


  @Autowired
  public OrderService(EmailService emailService, MenuPositionService menuPositionService) {
    this.emailService = emailService;
    this.menuPositionService = menuPositionService;
  }

  public Order createOrder(){
    Order created = new Order();
    created.setBoughtPositions(new ArrayList<>());
    return created;
  }

  @Transactional
  public OrderReadModel saveOrder(Order toSave){
    em.persist(toSave);
    return new OrderReadModel(toSave);
  }


  private BoughtPosition createBoughtPosition(BoughtPositionWriteModel source, int orderId){
    BoughtPosition created = new BoughtPosition();
    created.setQuantity(source.getQuantity());
    created.setOrder(findOrderById(orderId));
    created.setMenuPosition(menuPositionService.findById(source.getMenuPositionId()));
    return created;
  }

  private void throwErrorIfCommited(Order order)throws IllegalStateException{
    if(order.isCommited()){
      throw new IllegalStateException("This order is already commited!");
    }
  }
  @Transactional
  public OrderReadModel increaseBoughtPositionInCurrentOrder(BoughtPositionWriteModel addition, int orderId){
    //get current order or create new one
    Order currentOrder;
    try{
      currentOrder = findOrderById(orderId);
      throwErrorIfCommited(currentOrder);
    } catch (IllegalArgumentException e){
      currentOrder = createOrder();
      System.out.println("creating! id:"+currentOrder.getId());
    }

    //check if i have addition already in order
    if(currentOrder.getBoughtPositions().stream().anyMatch(
        el -> el.getMenuPosition().getId() == addition.getMenuPositionId())){

      //if so, just increase the quantity
      currentOrder.getBoughtPositions().forEach(element ->{
        if(element.getMenuPosition().getId()==addition.getMenuPositionId()){
          element.setQuantity(element.getQuantity()+addition.getQuantity());}
      });
    } else {
      //else, creaete new one and add to order
      var created = createBoughtPosition(addition, orderId);
      em.persist(created);
      currentOrder.getBoughtPositions().add(created);
    }
    return new OrderReadModel(currentOrder);
  }

  @Transactional
  public OrderReadModel decreaseBoughtPositionInCurrentOrder(int boughtPositionId, int orderId){
    Order currentOrder = findOrderById(orderId);

    //i would prefer stream or foreach, but else modifies source and needs to break immediately
    for(int i=0; i<currentOrder.getBoughtPositions().size();i++){
      BoughtPosition element = currentOrder.getBoughtPositions().get(i);
      if(element.getMenuPosition().getId()==boughtPositionId){
        if(element.getQuantity()==1){
          currentOrder.getBoughtPositions().remove(element);
          em.remove(element);
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
    Order currentOrder = findOrderById(orderId);

    currentOrder.setComment(order.getComment());
    currentOrder.setCommitedTime(LocalDateTime.now());
    currentOrder.setCommited(true);
    OrderCommitedReadModel commited = new OrderCommitedReadModel(currentOrder);
    executor.submit(() -> {
      emailService.sendEmail(
          "Zamówienie nr "+currentOrder.getId(),
          "majewski.piotr.511@gmail.com",
          "piomaj4@wp.pl",
          commited.toFreemarkerModel());
    });
    return commited;
  }

  public List<OrderReadModel> findAll(){
    TypedQuery<Order> query = em.createQuery(
        "SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.boughtPositions b " +
            "LEFT JOIN FETCH b.menuPosition m ",
        Order.class
        )
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);

    List<Order> result = query.getResultList();
    return result.stream().map(OrderReadModel::new).collect(Collectors.toList());
  }

  public List<OrderHistoryReadModel> findAllCommited(boolean isCommited){
    TypedQuery<Order> query = em.createQuery(
        "SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.boughtPositions b " +
            "LEFT JOIN FETCH b.menuPosition m " +
            "WHERE o.commited = :isCommited",
        Order.class
    )
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false)
        .setParameter("isCommited",isCommited);

    List<Order> result = query.getResultList();
    return result.stream().map(OrderHistoryReadModel::new).collect(Collectors.toList());
  }

  private Order findOrderById(int id){
    Order result;
    try{
      result = em.createQuery(
        "SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.boughtPositions b " +
            "LEFT JOIN FETCH b.menuPosition m " +
            "WHERE o.id = :id",
          Order.class)
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false)
        .setParameter("id",id)
        .getSingleResult();
      return result;
    }catch(NoResultException e){
      throw new IllegalArgumentException("no such order!");
    }
  }

  private BoughtPosition findBoughtPositionById(int id){
    TypedQuery<BoughtPosition> query = em.createQuery(
        "SELECT b FROM BoughtPosition b " +
            "LEFT JOIN FETCH b.menuPosition bm " +
            "WHERE b.id = :id",
        BoughtPosition.class
    ).setParameter("id",id);
    try{
      return query.getSingleResult();
    }catch(NoResultException e ){
      throw new IllegalArgumentException("No such bought position!");
    }
  }



}
