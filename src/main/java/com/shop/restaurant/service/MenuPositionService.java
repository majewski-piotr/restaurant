package com.shop.restaurant.service;

import com.shop.restaurant.exception.PositionNotFoundException;
import com.shop.restaurant.model.Position;
import com.shop.restaurant.persistence.CategoryEntity;
import com.shop.restaurant.persistence.PositionEntity;
import com.shop.restaurant.utils.PositionConverter;
import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuPositionService {
  CategoryService categoryService;

  @Autowired
  public MenuPositionService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PersistenceContext
  EntityManager entityManager;



  @Transactional
  public void savePosition(PositionEntity source){
    CategoryEntity categoryEntity = source.getCategory();
    if(categoryEntity.isFixedCost()){
      source.setCost(
          categoryEntity.getFixedCostValue()
      );
    }
    entityManager.persist(source);
  }

  public List<Position> findAll(){
    TypedQuery<PositionEntity> query = entityManager.createQuery(
        "SELECT DISTINCT m FROM PositionEntity m LEFT JOIN FETCH m.categoryEntity c", PositionEntity.class)
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);
    return query.getResultList().stream()
        .map(PositionConverter::createModel)
        .collect(Collectors.toList());
  }

  PositionEntity findById(int id) throws PositionNotFoundException{
    TypedQuery<PositionEntity> query = entityManager.createQuery(
        "SELECT m FROM PositionEntity m LEFT JOIN FETCH m.categoryEntity WHERE m.id = :id",
        PositionEntity.class
    ).setParameter("id",id);
    try{
      return query.getSingleResult();
    }catch(NoResultException e){
      throw new PositionNotFoundException();
    }
  }
}
