package com.binhlaig.pos.shopfeature;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopFeatureRepository extends JpaRepository<ShopFeature, Long> {
    Optional<ShopFeature> findByShopId(Long shopId);
    Optional<ShopFeature> findByShopCode(String shopCode);
}
