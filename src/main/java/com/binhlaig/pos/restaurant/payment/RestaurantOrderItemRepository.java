package com.binhlaig.pos.restaurant.payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOrderItemRepository extends JpaRepository<RestaurantOrderItem, Long> {
}
