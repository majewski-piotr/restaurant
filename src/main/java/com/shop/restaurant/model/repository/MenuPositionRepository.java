package com.shop.restaurant.model.repository;

import com.shop.restaurant.model.MenuPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuPositionRepository extends JpaRepository<MenuPosition,Integer> {
}
