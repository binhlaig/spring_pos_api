package com.binhlaig.pos.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopPlanOverrideRepository extends JpaRepository<ShopPlanOverride, Long> {

    Optional<ShopPlanOverride> findByShopIdAndActiveTrue(Long shopId);

    Optional<ShopPlanOverride> findByShopId(Long shopId);
}
