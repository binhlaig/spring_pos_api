package com.binhlaig.pos.restaurant.repository;

import com.binhlaig.pos.restaurant.entity.KitchenTicketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KitchenTicketItemRepository extends JpaRepository<KitchenTicketItem, Long> {

    @Query("""
            select i
            from KitchenTicketItem i
            join fetch i.ticket t
            where i.id = :itemId
              and t.shopId = :shopId
            """)
    Optional<KitchenTicketItem> findByIdAndTicketShopId(
            @Param("itemId") Long itemId,
            @Param("shopId") Long shopId
    );
}
