////
////
////package com.binhlaig.pos.staff.entity;
////
////import jakarta.persistence.*;
////import lombok.*;
////
////import java.math.BigDecimal;
////import java.time.LocalDate;
////
////@Entity
////@Table(name = "staff")
////@Getter
////@Setter
////@NoArgsConstructor
////@AllArgsConstructor
////@Builder
////public class Staff {
////
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
////
////    @Column(name = "full_name")
////    private String fullName;
////
////    @Column(nullable = false, unique = true)
////    private String email;
////
////    private String phone;
////
////    private String nrc;
////
////    @Column(name = "staff_id", unique = true)
////    private Long staffId;
////
////    private String password;
////
////    @Column(name = "date_of_birth")
////    private LocalDate dateOfBirth;
////
////    private String role;
////
////    private String branch;
////
////    private String status;
////
////    @Column(name = "start_date")
////    private LocalDate startDate;
////
////    private BigDecimal salary;
////
////    private String address;
////
////    @Column(name = "emergency_contact")
////    private String emergencyContact;
////
////    @Column(name = "emergency_phone")
////    private String emergencyPhone;
////
////    @Column(columnDefinition = "TEXT")
////    private String note;
////
////    @Column(name = "image_url")
////    private String imageUrl;
////}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//package com.binhlaig.pos.staff.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "staff")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Staff {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "full_name")
//    private String fullName;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    private String phone;
//
//    private String nrc;
//
//    @Column(name = "staff_id", unique = true)
//    private Long staffId;
//
//    private String password;
//
//    @Column(name = "date_of_birth")
//    private LocalDate dateOfBirth;
//
//    // ✅ ADD THIS (VERY IMPORTANT)
//    @Column(name = "shop_id", nullable = false)
//    private Long shopId;
//
//    private String role;
//
//    private String branch;
//
//    private String status;
//
//    @Column(name = "start_date")
//    private LocalDate startDate;
//
//    private BigDecimal salary;
//
//    private String address;
//
//    @Column(name = "emergency_contact")
//    private String emergencyContact;
//
//    @Column(name = "emergency_phone")
//    private String emergencyPhone;
//
//    @Column(columnDefinition = "TEXT")
//    private String note;
//
//    @Column(name = "image_url")
//    private String imageUrl;
//}


























package com.binhlaig.pos.staff.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String nrc;

    @Column(name = "staff_id", unique = true)
    private Long staffId;

    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "shop_code", nullable = false)
    private String shopCode;

    private String role;

    private String branch;

    private String status;

    @Column(name = "start_date")
    private LocalDate startDate;

    private BigDecimal salary;

    private String address;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "emergency_phone")
    private String emergencyPhone;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "user_id")
    private Long userId;
}