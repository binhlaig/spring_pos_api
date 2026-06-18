package com.binhlaig.pos.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopUsageMonthlyRepository extends JpaRepository<ShopUsageMonthly, Long> {

    Optional<ShopUsageMonthly> findByShopIdAndYearAndMonth(Long shopId, Integer year, Integer month);
}
