package com.binhlaig.pos.restaurant.repository;

import com.binhlaig.pos.restaurant.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {

    List<RestaurantTable> findByShopIdOrderByFloorNameAscTableNoAsc(Long shopId);

    Optional<RestaurantTable> findByIdAndShopId(Long id, Long shopId);

    boolean existsByShopIdAndTableNoIgnoreCase(Long shopId, String tableNo);

    boolean existsByShopIdAndTableNoIgnoreCaseAndIdNot(Long shopId, String tableNo, Long id);
}
