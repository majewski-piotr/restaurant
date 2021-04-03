package com.shop.restaurant.service;

import com.shop.restaurant.model.Order;
import com.shop.restaurant.persistence.BoughtPositionEntity;
import com.shop.restaurant.persistence.OrderEntity;
import com.shop.restaurant.utils.OrderConverter;
import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.shop.restaurant.utils.OrderConverter.*;

@Service
public class OrderService {
  EmailService emailService;
  MenuPositionService menuPositionService;
  ExecutorService executor = Executors.newFixedThreadPool(1);

  @PersistenceContext
  EntityManager entityManager;


  @Autowired
  public OrderService(EmailService emailService, MenuPositionService menuPositionService) {
    this.emailService = emailService;
    this.menuPositionService = menuPositionService;
  }

  public Order updateCost(Order source){
    source.getBoughtPositions().forEach(
        boughtPosition -> {
          source.setCost(
              source.getCost() + boughtPosition.getCost() * boughtPosition.getQuantity()
          );
        }
    );
    return source;
  }


  @Transactional
  public Order saveOrder(Order source){
    OrderEntity created = new OrderEntity();
    created.setComment(source.getComment());
    created.setCommitedTime(LocalDateTime.now());
    created.setBoughtPositions(
        source.getBoughtPositions().stream().map(model -> {
          BoughtPositionEntity boughtPositionEntity = new BoughtPositionEntity();
          boughtPositionEntity.setMenuPosition(menuPositionService.findById(model.getPositionId()));
          boughtPositionEntity.setQuantity(model.getQuantity());
          boughtPositionEntity.setOrder(created);
          return boughtPositionEntity;
        }).collect(Collectors.toList())
    );

    entityManager.persist(created);

    return updateCost(createModel(created));
  }

  public List<Order> findAll(){
    TypedQuery<OrderEntity> query = entityManager.createQuery(
        "SELECT DISTINCT o FROM OrderEntity o " +
            "LEFT JOIN FETCH o.boughtPositionEntities b " +
            "LEFT JOIN FETCH b.positionEntity m ",
        OrderEntity.class
        )
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);

    List<OrderEntity> result = query.getResultList();
    return result.stream().map(OrderConverter::createModel).collect(Collectors.toList());
  }


  private OrderEntity findOrderById(int id){
    OrderEntity result;
    try{
      result = entityManager.createQuery(
        "SELECT DISTINCT o FROM OrderEntity o " +
            "LEFT JOIN FETCH o.boughtPositionEntities b " +
            "LEFT JOIN FETCH b.positionEntity m " +
            "WHERE o.id = :id",
          OrderEntity.class)
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false)
        .setParameter("id",id)
        .getSingleResult();
      return result;
    }catch(NoResultException e){
      throw new IllegalArgumentException("no such order!");
    }
  }

  private BoughtPositionEntity findBoughtPositionById(int id){
    TypedQuery<BoughtPositionEntity> query = entityManager.createQuery(
        "SELECT b FROM BoughtPositionEntity b " +
            "LEFT JOIN FETCH b.positionEntity bm " +
            "WHERE b.id = :id",
        BoughtPositionEntity.class
    ).setParameter("id",id);
    try{
      return query.getSingleResult();
    }catch(NoResultException e ){
      throw new IllegalArgumentException("No such bought position!");
    }
  }
}
