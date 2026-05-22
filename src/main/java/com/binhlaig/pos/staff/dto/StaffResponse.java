////
////package com.binhlaig.pos.staff.dto;
////
////import lombok.Builder;
////import lombok.Getter;
////import lombok.Setter;
////
////import java.math.BigDecimal;
////import java.time.LocalDate;
////
////@Getter
////@Setter
////@Builder
////public class StaffResponse {
////
////    private Long id;
////
////    private String fullName;
////    private String email;
////    private String phone;
////    private String nrc;
////
////    private Long staffId;
////
////    private LocalDate dateOfBirth;
////
////    private String role;
////    private String branch;
////    private String status;
////
////    private LocalDate startDate;
////
////    private BigDecimal salary;
////
////    private String address;
////
////    private String emergencyContact;
////    private String emergencyPhone;
////
////    private String note;
////
////    private String imageUrl;
////}
//
//
//
//
//package com.binhlaig.pos.staff.dto;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Getter
//@Setter
//@Builder
//public class StaffResponse {
//
//    private Long id;
//
//    private String fullName;
//    private String email;
//    private String phone;
//    private String nrc;
//
//    private Long staffId;
//
//    private LocalDate dateOfBirth;
//
//    // ✅ ADD THIS
//    private Long shopId;
//
//    private String role;
//    private String branch;
//    private String status;
//
//    private LocalDate startDate;
//
//    private BigDecimal salary;
//
//    private String address;
//
//    private String emergencyContact;
//    private String emergencyPhone;
//
//    private String note;
//
//    private String imageUrl;
//}







package com.binhlaig.pos.staff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {

    private Long id;

    private String fullName;
    private String email;
    private String phone;
    private String nrc;

    private Long staffId;

    private LocalDate dateOfBirth;

    private Long shopId;
    private String shopCode;

    private String role;
    private String branch;
    private String status;

    private LocalDate startDate;

    private BigDecimal salary;

    private String address;

    private String emergencyContact;
    private String emergencyPhone;

    private String note;

    private String imageUrl;

    private String message;
}