package com.binhlaig.pos.staff.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class StaffRequest {

    private String fullName;
    private String email;
    private String phone;
    private String nrc;

    private Long staffId;
    private String password;

    private LocalDate dateOfBirth;

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
}