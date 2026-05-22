//
//
//
//
//
//
//package com.binhlaig.pos.staff.repository;
//
//import com.binhlaig.pos.staff.entity.Staff;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface StaffRepository extends JpaRepository<Staff, Long> {
//
//    Optional<Staff> findByEmail(String email);
//
//    boolean existsByEmail(String email);
//
//    boolean existsByEmailAndIdNot(String email, Long id);
//
//    Optional<Staff> findByStaffId(Long staffId);
//
//    boolean existsByStaffId(Long staffId);
//
//    boolean existsByStaffIdAndIdNot(Long staffId, Long id);
//
//    List<Staff> findByShopId(Long shopId);
//
//    Optional<Staff> findByIdAndShopId(Long id, Long shopId);
//
//    boolean existsByEmailAndShopId(String email, Long shopId);
//
//    boolean existsByStaffIdAndShopId(Long staffId, Long shopId);
//
//    Optional<Staff> findByStaffIdAndShopId(Long staffId, Long shopId);
//
//    Optional<Staff> findByShopCodeAndStaffId(String shopCode, Long staffId);
//
//    Optional<Staff> findFirstByShopIdAndFullNameIgnoreCase(Long shopId, String fullName);
//}






package com.binhlaig.pos.staff.repository;

import com.binhlaig.pos.staff.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Staff> findByStaffId(Long staffId);

    boolean existsByStaffId(Long staffId);

    boolean existsByStaffIdAndIdNot(Long staffId, Long id);

    List<Staff> findByShopId(Long shopId);

    Optional<Staff> findByIdAndShopId(Long id, Long shopId);

    boolean existsByEmailAndShopId(String email, Long shopId);

    boolean existsByStaffIdAndShopId(Long staffId, Long shopId);

    Optional<Staff> findByStaffIdAndShopId(Long staffId, Long shopId);

    Optional<Staff> findByShopCodeAndStaffId(String shopCode, Long staffId);

    Optional<Staff> findFirstByUserId(Long userId);
}