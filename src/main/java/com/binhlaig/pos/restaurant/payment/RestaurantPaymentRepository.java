package com.binhlaig.pos.restaurant.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantPaymentRepository extends JpaRepository<RestaurantPayment, Long> {

    Optional<RestaurantPayment> findByIdAndShopId(Long id, Long shopId);

    List<RestaurantPayment> findByShopIdAndShopCodeOrderByCreatedAtDesc(Long shopId, String shopCode);
}
