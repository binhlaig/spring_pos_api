//
//package com.binhlaig.pos.receipt.repository;
//
//import com.binhlaig.pos.receipt.entity.PosReceipt;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface PosReceiptRepository extends JpaRepository<PosReceipt, Long> {
//    Optional<PosReceipt> findByReceiptNo(String receiptNo);
//
//    List<PosReceipt> findByCreatedByUserIdOrderByCreatedAtDesc(Long createdByUserId);
//
//    List<PosReceipt> findByShopIdOrderByCreatedAtDesc(Long shopId);
//}





package com.binhlaig.pos.receipt.repository;

import com.binhlaig.pos.receipt.entity.PosReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PosReceiptRepository extends JpaRepository<PosReceipt, Long> {

    Optional<PosReceipt> findByReceiptNo(String receiptNo);

    Optional<PosReceipt> findByReceiptNoAndShopId(String receiptNo, Long shopId);

    List<PosReceipt> findByCreatedByUserIdOrderByCreatedAtDesc(Long createdByUserId);

    List<PosReceipt> findByShopIdOrderByCreatedAtDesc(Long shopId);
}