package com.binhlaig.pos.receiptsetting.repository;

import com.binhlaig.pos.receiptsetting.entity.ReceiptSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiptSettingRepository extends JpaRepository<ReceiptSetting, Long> {

    Optional<ReceiptSetting> findByShopId(Long shopId);

    boolean existsByShopId(Long shopId);
}