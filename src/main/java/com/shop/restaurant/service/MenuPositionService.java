package com.shop.restaurant.service;

import com.shop.restaurant.model.MenuPositionReadModel;
import com.shop.restaurant.model.MenuPositionWriteModel;
import com.shop.restaurant.persistence.MenuPosition;
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

  private MenuPosition create(MenuPositionWriteModel source){
    MenuPosition created = new MenuPosition();

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
    MenuPosition created = create(source);
    em.persist(created);
    return new MenuPositionReadModel(created);
  }

  public List<MenuPositionReadModel> findAll(){
    TypedQuery<MenuPosition> query = em.createQuery(
        "SELECT DISTINCT m FROM MenuPosition m LEFT JOIN FETCH m.category c",MenuPosition.class)
        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH,false);
    return query.getResultList().stream()
        .map(MenuPositionReadModel::new)
        .collect(Collectors.toList());
  }

  MenuPosition findById(int id){
    TypedQuery<MenuPosition> query = em.createQuery(
        "SELECT m FROM MenuPosition m LEFT JOIN FETCH m.category WHERE m.id = :id",
        MenuPosition.class
    ).setParameter("id",id);
    try{
      return query.getSingleResult();
    }catch(NoResultException e){
      throw new IllegalArgumentException("No such menu position!");
    }
  }
}
