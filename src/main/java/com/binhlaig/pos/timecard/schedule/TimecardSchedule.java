package com.binhlaig.pos.timecard.schedule;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
        name = "timecard_schedules",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_timecard_schedules_shop_staff_date",
                columnNames = {"shop_id", "staff_id", "schedule_date"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimecardSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "shop_code", nullable = false, length = 50)
    private String shopCode;

    @Column(name = "staff_id", nullable = false, length = 50)
    private String staffId;

    @Column(name = "staff_name", nullable = false, length = 180)
    private String staffName;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(length = 100)
    private String role;

    @Column(columnDefinition = "text")
    private String note;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TimecardScheduleStatus status = TimecardScheduleStatus.DRAFT;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
