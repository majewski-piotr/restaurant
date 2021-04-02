package com.shop.restaurant.service;

import com.shop.restaurant.persistence.CategoryEntity;
import com.shop.restaurant.persistence.MenuPosition;
import com.shop.restaurant.model.CategoryReadModel;
import com.shop.restaurant.model.CategoryWriteModel;
import com.shop.restaurant.model.MenuPositionReadModel;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
  @PersistenceContext
  EntityManager em;

  private CategoryEntity create(CategoryWriteModel source){
    CategoryEntity created = new CategoryEntity();
    created.setName(source.getName());
    if(source.isFixedCost()){
      created.setFixedCost(source.isFixedCost());
      created.setFixedCostValue(source.getFixedCostValue());
    }
    return created;
  }

  @Transactional
  public CategoryReadModel save(CategoryWriteModel source){
    CategoryEntity created = create(source);
    em.persist(created);
    return new CategoryReadModel(created);
  }

  public List<CategoryEntity> findAll(){
    TypedQuery<CategoryEntity> query = em.createQuery(
        "SELECT DISTINCT c FROM CategoryEntity c", CategoryEntity.class
    ).setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);

    return query.getResultList();
  }
  public List<MenuPosition> findByCategoryId(int id){
    TypedQuery<MenuPosition> query = em.createQuery(
        "SELECT DISTINCT m FROM MenuPosition m WHERE m.category.id = :id",
        MenuPosition.class
    )
        .setParameter("id",id)
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);

    return query.getResultList();
  }

  CategoryEntity findById(int id){
    TypedQuery<CategoryEntity> query = em.createQuery(
        "SELECT c FROM CategoryEntity c where c.id = :id", CategoryEntity.class
    ).setParameter("id",id);
    try{
      return query.getSingleResult();
    } catch (NoResultException e ){
      throw new IllegalArgumentException("No such category");
    }
  }

}
