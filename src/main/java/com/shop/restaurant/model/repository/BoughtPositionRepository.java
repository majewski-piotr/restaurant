package com.shop.restaurant.model.repository;

import com.shop.restaurant.model.BoughtPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoughtPositionRepository extends JpaRepository<BoughtPosition,Integer> {
}
