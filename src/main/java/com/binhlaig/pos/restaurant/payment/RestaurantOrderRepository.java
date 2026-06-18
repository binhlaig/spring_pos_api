package com.binhlaig.pos.restaurant.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrder, Long> {

    Optional<RestaurantOrder> findByIdAndShopId(Long id, Long shopId);

    Optional<RestaurantOrder> findFirstByShopIdAndTableIdAndStatusOrderByCreatedAtDesc(
            Long shopId,
            Long tableId,
            String status
    );

    @Query("""
            select distinct o
            from RestaurantOrder o
            left join fetch o.items
            where o.shopId = :shopId
              and o.tableId = :tableId
              and o.status = :status
            order by o.createdAt desc
            """)
    Optional<RestaurantOrder> findOpenByTableIdWithItems(
            @Param("shopId") Long shopId,
            @Param("tableId") Long tableId,
            @Param("status") String status
    );
}
