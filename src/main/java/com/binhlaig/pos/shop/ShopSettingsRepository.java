package com.binhlaig.pos.shop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopSettingsRepository extends JpaRepository<ShopSettings, Long> {

    Optional<ShopSettings> findByShopId(Long shopId);
}