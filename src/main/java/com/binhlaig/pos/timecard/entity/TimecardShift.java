//package com.binhlaig.pos.timecard.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "timecard_shifts")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class TimecardShift {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "employee_id", nullable = false)
//    private Long employeeId;
//
//    @Column(name = "work_date", nullable = false)
//    private LocalDate workDate;
//
//    @Column(name = "clock_in_time", nullable = false)
//    private LocalDateTime clockInTime;
//
//    @Column(name = "clock_out_time")
//    private LocalDateTime clockOutTime;
//
//    @Column(columnDefinition = "TEXT")
//    private String note;
//
//    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<TimecardBreak> breaks = new ArrayList<>();
//}







package com.binhlaig.pos.timecard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "timecard_shifts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimecardShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "clock_in_time", nullable = false)
    private LocalDateTime clockInTime;

    @Column(name = "clock_out_time")
    private LocalDateTime clockOutTime;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "shop_code")
    private String shopCode;

    @Builder.Default
    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TimecardBreak> breaks = new ArrayList<>();
}