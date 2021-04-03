package com.shop.restaurant.service;

import com.shop.restaurant.model.MenuPositionReadModel;
import com.shop.restaurant.model.MenuPositionWriteModel;
import com.shop.restaurant.persistence.PositionEntity;
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
  EntityManager em;

  private PositionEntity create(MenuPositionWriteModel source){
    PositionEntity created = new PositionEntity();

    created.setName(source.getName());
    created.setCategory(categoryService.findById(source.getCategoryId()));

    if(created.getCategory().isFixedCost()){
      created.setCost(created.getCategory().getFixedCostValue());
    }else{
      created.setCost(source.getCost());
    }
    return created;
  }

  @Transactional
  public MenuPositionReadModel save(MenuPositionWriteModel source){
    PositionEntity created = create(source);
    em.persist(created);
    return new MenuPositionReadModel(created);
  }

  public List<MenuPositionReadModel> findAll(){
    TypedQuery<PositionEntity> query = em.createQuery(
        "SELECT DISTINCT m FROM PositionEntity m LEFT JOIN FETCH m.category c", PositionEntity.class)
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);
    return query.getResultList().stream()
        .map(MenuPositionReadModel::new)
        .collect(Collectors.toList());
  }

  PositionEntity findById(int id){
    TypedQuery<PositionEntity> query = em.createQuery(
        "SELECT m FROM PositionEntity m LEFT JOIN FETCH m.category WHERE m.id = :id",
        PositionEntity.class
    ).setParameter("id",id);
    try{
      return query.getSingleResult();
    }catch(NoResultException e){
      throw new IllegalArgumentException("No such menu position!");
    }
  }
}
