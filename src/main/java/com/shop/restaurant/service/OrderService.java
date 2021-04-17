package com.shop.restaurant.service;

import com.shop.restaurant.exception.BoughtPositionNotFoundException;
import com.shop.restaurant.exception.CategoryNotFoundException;
import com.shop.restaurant.exception.OrderNotFoundException;
import com.shop.restaurant.exception.PositionNotFoundException;
import com.shop.restaurant.model.BoughtPosition;
import com.shop.restaurant.model.Order;
import com.shop.restaurant.persistence.BoughtPositionEntity;
import com.shop.restaurant.persistence.CategoryEntity;
import com.shop.restaurant.persistence.OrderEntity;
import com.shop.restaurant.utils.OrderConverter;
import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static com.shop.restaurant.utils.OrderConverter.createModel;
import static com.shop.restaurant.utils.OrderConverter.createFreemarkerModel;

@Service
public class OrderService {
  EmailService emailService;
  MenuPositionService menuPositionService;
  CategoryService categoryService;
  ExecutorService executor = Executors.newFixedThreadPool(1);

  @PersistenceContext
  EntityManager entityManager;

  @Value("${mail.sender.username}")
  private String sender;
  @Value("${mail.receiver.username}")
  private String receiver;

  @Autowired
  public OrderService(EmailService emailService, MenuPositionService menuPositionService, CategoryService categoryService) {
    this.emailService = emailService;
    this.menuPositionService = menuPositionService;
    this.categoryService = categoryService;
  }

  public Order updateCost(Order source) throws PositionNotFoundException, CategoryNotFoundException {
    source.setCost(0);
    for (BoughtPosition boughtPosition : source.getBoughtPositions()) {
      CategoryEntity categoryEntity = categoryService.findById(menuPositionService.findById(
          boughtPosition.getPositionId()).getCategory().getId());
      if (categoryEntity.isFixedCost()) {
        source.setCost(
            source.getCost() + categoryEntity.getFixedCostValue() * boughtPosition.getQuantity()
        );
      } else {
        source.setCost(
            source.getCost() + boughtPosition.getCost() * boughtPosition.getQuantity()
        );
      }
    }
    return source;
  }


  @Transactional
  public Order saveOrder(Order source) throws PositionNotFoundException, CategoryNotFoundException {
    OrderEntity created = new OrderEntity();
    created.setComment(source.getComment());
    created.setCommitedTime(LocalDateTime.now());
    created.setBoughtPositions(
        source.getBoughtPositions().stream().map(model -> {
          BoughtPositionEntity boughtPositionEntity = new BoughtPositionEntity();
          try {
            boughtPositionEntity.setMenuPosition(menuPositionService.findById(model.getPositionId()));
          } catch (PositionNotFoundException e) {
            throw new RuntimeException(e);
          }
          boughtPositionEntity.setQuantity(model.getQuantity());
          boughtPositionEntity.setOrder(created);
          return boughtPositionEntity;
        }).collect(Collectors.toList())
    );

    executor.submit(()->{
      emailService.sendEmail(
          "Zamówienie nr "+created.getId(),
          sender,
          receiver,
          createFreemarkerModel(created));
        }
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


  private OrderEntity findOrderById(int id) throws OrderNotFoundException{
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
      throw new OrderNotFoundException();
    }
  }

  private BoughtPositionEntity findBoughtPositionById(int id) throws BoughtPositionNotFoundException {
    TypedQuery<BoughtPositionEntity> query = entityManager.createQuery(
        "SELECT b FROM BoughtPositionEntity b " +
            "LEFT JOIN FETCH b.positionEntity bm " +
            "WHERE b.id = :id",
        BoughtPositionEntity.class
    ).setParameter("id",id);
    try{
      return query.getSingleResult();
    }catch(NoResultException e ){
      throw new BoughtPositionNotFoundException();
    }
  }
}
