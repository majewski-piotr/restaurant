package com.shop.restaurant.model.repository;

import com.shop.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
  List<Order> findByCommited(boolean commited);
}
