package com.shop.restaurant.service;

import com.shop.restaurant.persistence.CategoryEntity;
import com.shop.restaurant.persistence.PositionEntity;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class CategoryService {
  @PersistenceContext
  EntityManager entityManager;


  @Transactional
  public void saveCategory(CategoryEntity source){
    entityManager.persist(source);
  }

  public List<CategoryEntity> findAll(){
    TypedQuery<CategoryEntity> query = entityManager.createQuery(
        "SELECT DISTINCT c FROM CategoryEntity c", CategoryEntity.class
    ).setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);

    return query.getResultList();
  }
  public List<PositionEntity> findByCategoryId(int id){
    TypedQuery<PositionEntity> query = entityManager.createQuery(
        "SELECT DISTINCT m FROM PositionEntity m WHERE m.categoryEntity.id = :id",
        PositionEntity.class
    )
        .setParameter("id",id)
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);

    return query.getResultList();
  }

  CategoryEntity findById(int id){
    TypedQuery<CategoryEntity> query = entityManager.createQuery(
        "SELECT c FROM CategoryEntity c where c.id = :id", CategoryEntity.class
    ).setParameter("id",id);
    try{
      return query.getSingleResult();
    } catch (NoResultException e ){
      throw new IllegalArgumentException("No such category");
    }
  }

}
