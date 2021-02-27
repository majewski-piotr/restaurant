package com.shop.restaurant.service;

import com.shop.restaurant.model.Category;
import com.shop.restaurant.model.dto.CategoryReadModel;
import com.shop.restaurant.model.dto.CategoryWriteModel;
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

  private Category create(CategoryWriteModel source){
    Category created = new Category();
    created.setName(source.getName());
    if(source.isFixedCost()){
      created.setFixedCost(source.isFixedCost());
      created.setFixedCostValue(source.getFixedCostValue());
    }
    return created;
  }

  @Transactional
  public CategoryReadModel save(CategoryWriteModel source){
    Category created = create(source);
    em.persist(created);
    return new CategoryReadModel(created);
  }

  public List<CategoryReadModel> findAll(){
    TypedQuery<Category> query = em.createQuery(
        "SELECT DISTINCT c FROM Category c",Category.class
    ).setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);

    return query.getResultList().stream()
        .map(CategoryReadModel::new)
        .collect(Collectors.toList());
  }

  Category findById(int id){
    TypedQuery<Category> query = em.createQuery(
        "SELECT c FROM Category c where c.id = :id", Category.class
    ).setParameter("id",id);
    try{
      return query.getSingleResult();
    } catch (NoResultException e ){
      throw new IllegalArgumentException("No such category");
    }
  }
}
